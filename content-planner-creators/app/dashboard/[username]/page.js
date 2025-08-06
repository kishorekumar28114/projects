"use client";
import React, { useState } from 'react';
import Dashboard from '../../../components/Dashboard';
import ChallengeForm from '../../../components/ChallengeForm';
import { useParams } from 'next/navigation';

const UserDashboardPage = () => {
  const params = useParams();
  const username = params?.username;
  const [showForm, setShowForm] = useState(false);

  return (
    <div>
      <Dashboard username={username} onAddChallenge={() => setShowForm(true)} />
      {showForm && (
        <div className="modal">
          <ChallengeForm
            username={username}
            onSuccess={() => { setShowForm(false); window.location.reload(); }}
            onCancel={() => setShowForm(false)}
          />
        </div>
      )}
    </div>
  );
};

export default UserDashboardPage;
