"use client";
import React, { useEffect, useState } from 'react';
import { useParams } from 'next/navigation';
import ChallengeForm from '../../../components/ChallengeForm';

const ChallengePage = () => {
  const params = useParams();
  const id = params?.id;

  const [challenge, setChallenge] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const [updating, setUpdating] = useState(false);
  const [editMode, setEditMode] = useState(false);

  useEffect(() => {
    if (!id) return;
    setLoading(true);
    setError('');
    fetch(`/api/challenge/${id}`)
      .then(res => res.json())
      .then(data => {
        if (data.challenge) setChallenge(data.challenge);
        else setError(data.message || 'Failed to fetch challenge');
      })
      .catch(() => setError('Network error'))
      .finally(() => setLoading(false));
  }, [id]);

  const handleCheckbox = async (idx, checked) => {
    setUpdating(true);
    setError('');
    try {
      const res = await fetch(`/api/challenge/${id}`, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ dayIdx: idx, posted: checked })
      });
      if (res.ok) {
        setChallenge(prev => {
          const newDays = [...prev.days];
          newDays[idx].posted = checked;
          return { ...prev, days: newDays };
        });
      } else {
        const data = await res.json();
        setError(data.message || 'Failed to update');
      }
    } catch {
      setError('Network error');
    } finally {
      setUpdating(false);
    }
  };

  const handleEditSubmit = async (formData) => {
    setUpdating(true);
    setError('');
    try {
      const updatedDays = formData.days.map(d => ({
        ...d,
        hashtags: typeof d.hashtags === 'string'
          ? d.hashtags.split(',').map(h => h.trim())
          : d.hashtags,
        posted: d.posted || false
      }));

      const res = await fetch(`/api/challenge/${id}`, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
          challengeType: formData.challengeType,
          days: updatedDays
        })
      });

      if (res.ok) {
        setEditMode(false);
        setChallenge(prev => ({
          ...prev,
          challengeType: formData.challengeType,
          days: updatedDays
        }));
      } else {
        const data = await res.json();
        setError(data.message || 'Failed to update');
      }
    } catch {
      setError('Network error');
    } finally {
      setUpdating(false);
    }
  };

  if (loading) return <div>Loading...</div>;
  if (error) return <div className="error-msg">{error}</div>;
  if (!challenge) return null;

  return (
    <div className="challenge-detail">
      <h1>{challenge.challengeType} Challenge</h1>
      <div>Created: {new Date(challenge.createdAt).toLocaleString()}</div>

      <button onClick={() => setEditMode(true)} disabled={editMode || updating} style={{ marginBottom: 8 }}>
        Edit Challenge
      </button>

      {editMode ? (
        <ChallengeForm
          username={challenge.username || ''}
          onSuccess={() => setEditMode(false)}
          onCancel={() => setEditMode(false)}
          initialChallengeType={challenge.challengeType}
          initialDays={challenge.days}
          editMode={true}
          onSubmit={handleEditSubmit}
        />
      ) : (
        <ul className="days-list">
          {challenge.days.map((day, idx) => (
            <li key={idx} className={`day-item${day.posted ? ' posted' : ''}`}>
              <label>
                <input
                  type="checkbox"
                  checked={!!day.posted}
                  disabled={updating}
                  onChange={e => handleCheckbox(idx, e.target.checked)}
                />
                <span>Day {idx + 1}: {day.title}</span>
              </label>
              {day.imagePrompt && <div>Prompt: {day.imagePrompt}</div>}
              {day.hashtags && day.hashtags.length > 0 && (
                <div>Hashtags: {Array.isArray(day.hashtags) ? day.hashtags.join(', ') : day.hashtags}</div>
              )}
            </li>
          ))}
        </ul>
      )}
    </div>
  );
};

export default ChallengePage;
