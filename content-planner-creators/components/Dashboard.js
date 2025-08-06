import React, { useEffect, useState } from 'react';
import ChallengeCard from './ChallengeCard';

const Dashboard = ({ username, onAddChallenge }) => {
  const [challenges, setChallenges] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');

  const handleDelete = async (challenge) => {
    try {
      const res = await fetch(`/api/challenge/${challenge._id}`, { method: 'DELETE' });
      if (res.ok) {
        setChallenges(prev => prev.filter(c => c._id !== challenge._id));
      } else {
        const data = await res.json();
        setError(data.message || 'Failed to delete challenge');
      }
    } catch {
      setError('Network error');
    }
  };

  useEffect(() => {
    if (!username) return;
    setLoading(true);
    setError('');
    fetch(`/api/dashboard?username=${encodeURIComponent(username)}`)
      .then(res => res.json())
      .then(data => {
        if (data.challenges) setChallenges(data.challenges);
        else setError(data.message || 'Failed to fetch challenges');
      })
      .catch(() => setError('Network error'))
      .finally(() => setLoading(false));
  }, [username]);

  return (
    <div className="dashboard-container">
      <div className="dashboard-header">
        <h2>{username ? `${username}'s Challenges` : 'Dashboard'}</h2>
        <button onClick={onAddChallenge} className="add-btn">+ Add Challenge</button>
      </div>
      {loading && <div>Loading...</div>}
      {error && <div className="error-msg">{error}</div>}
      <div className="challenge-list">
        {challenges.map(challenge => (
          <ChallengeCard
            key={challenge._id}
            challenge={challenge}
            onDelete={handleDelete}
          />
        ))}
        {challenges.length === 0 && !loading && !error && <div>No active challenges.</div>}
      </div>
    </div>
  );
};

export default Dashboard;
