import React from 'react';
import { useRouter } from 'next/navigation';

const ChallengeCard = ({ challenge, onDelete }) => {
  const router = useRouter();
  if (!challenge) return null;
  const completedDays = challenge.days.filter(d => d.posted).length;
  const isComplete = completedDays === challenge.days.length && challenge.days.length > 0;

  return (
    <div
      className={`challenge-card${isComplete ? ' complete' : ''}`}
      style={{ cursor: 'pointer' }}
      onClick={() => router.push(`/challenge/${challenge._id}`)}
    >
      <h3>{challenge.challengeType} Challenge</h3>
      <div>Days: {challenge.days.length}</div>
      <div>Posted: {completedDays} / {challenge.days.length}</div>
      <div>Status: {isComplete ? 'Complete' : 'Active'}</div>
      {onDelete && <button className="delete-btn" onClick={e => { e.stopPropagation(); onDelete(challenge); }}>Delete</button>}
    </div>
  );
};

export default ChallengeCard;
