// POST /api/challenge
import clientPromise from '../../../lib/mongodb';

export async function POST(request) {
  try {
    const body = await request.json();
    const { username, challengeType, days } = body;
    if (!username || !challengeType || !days) {
      return new Response(JSON.stringify({ message: 'Missing fields' }), { status: 400 });
    }
    const client = await clientPromise;
    const db = client.db();
    const user = await db.collection('users').findOne({ username });
    if (!user) {
      return new Response(JSON.stringify({ message: 'User not found' }), { status: 404 });
    }
    const challenge = {
      userId: user._id,
      challengeType,
      days,
      createdAt: new Date()
    };
    await db.collection('challenges').insertOne(challenge);
    return new Response(JSON.stringify({ message: 'Challenge created' }), { status: 201 });
  } catch (e) {
    return new Response(JSON.stringify({ message: 'Server error' }), { status: 500 });
  }
}
