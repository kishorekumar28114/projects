import React, { useState } from 'react';

const challengeTypes = ['Reels', 'Shorts', 'Video'];

const ChallengeForm = ({ username, onSuccess, onCancel, initialChallengeType, initialDays, editMode, onSubmit }) => {
  const [challengeType, setChallengeType] = useState(initialChallengeType || challengeTypes[0]);
  const [numDays, setNumDays] = useState(initialDays ? initialDays.length : 7);
  const [days, setDays] = useState(initialDays || Array(7).fill({ title: '', imagePrompt: '', hashtags: '' }));
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');

  const handleNumDaysChange = (e) => {
    const n = parseInt(e.target.value, 10);
    setNumDays(n);
    setDays(Array(n).fill({ title: '', imagePrompt: '', hashtags: '' }));
  };

  const handleDayChange = (idx, field, value) => {
    setDays(prev => prev.map((d, i) => i === idx ? { ...d, [field]: value } : d));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    setError('');
    try {
      if (editMode && onSubmit) {
        await onSubmit({ challengeType, days });
        if (onSuccess) onSuccess();
      } else {
        const res = await fetch('/api/challenge', {
          method: 'POST',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify({
            username,
            challengeType,
            days: days.map(d => ({
              ...d,
              hashtags: d.hashtags.split(',').map(h => h.trim()),
              posted: false
            }))
          })
        });
        const data = await res.json();
        if (res.ok) {
          if (onSuccess) onSuccess();
        } else {
          setError(data.message || 'Failed to create challenge');
        }
      }
    } catch {
      setError('Network error');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className={`challenge-form-modal${editMode ? ' edit-mode' : ''}`}>
      <form onSubmit={handleSubmit}>
        <h2>{editMode ? 'Edit Challenge' : 'Create Challenge'}</h2>
        <label>Type:</label>
        <select value={challengeType} onChange={e => setChallengeType(e.target.value)}>
          {challengeTypes.map(type => <option key={type}>{type}</option>)}
        </select>
        <label>Number of Days:</label>
        <input type="number" min={1} max={30} value={numDays} onChange={handleNumDaysChange} />
        <div className="days-fields">
          {days.map((day, idx) => (
            <div key={idx} className="day-field">
              <h4>Day {idx + 1}</h4>
              <input
                type="text"
                placeholder="Title"
                value={day.title}
                onChange={e => handleDayChange(idx, 'title', e.target.value)}
                required
              />
              <input
                type="text"
                placeholder="Image Prompt"
                value={day.imagePrompt}
                onChange={e => handleDayChange(idx, 'imagePrompt', e.target.value)}
              />
              <input
                type="text"
                placeholder="Hashtags (comma separated)"
                value={day.hashtags}
                onChange={e => handleDayChange(idx, 'hashtags', e.target.value)}
              />
            </div>
          ))}
        </div>
        <button type="submit" disabled={loading}>{loading ? (editMode ? 'Saving...' : 'Saving...') : (editMode ? 'Save Changes' : 'Save Challenge')}</button>
        <button type="button" onClick={onCancel} disabled={loading}>Cancel</button>
        {error && <div className="error-msg">{error}</div>}
      </form>
    </div>
  );
};

export default ChallengeForm;
