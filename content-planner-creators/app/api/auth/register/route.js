// POST /api/auth/register
import clientPromise from '../../../../lib/mongodb';

export async function POST(request) {
  try {
    const body = await request.json();
    const { username, password } = body;
    if (!username || !password) {
      return new Response(JSON.stringify({ message: 'Missing fields' }), { status: 400 });
    }
    const client = await clientPromise;
    const db = client.db();
    const existing = await db.collection('users').findOne({ username });
    if (existing) {
      return new Response(JSON.stringify({ message: 'Username already exists' }), { status: 409 });
    }
    // For demo: store password as plain text
    await db.collection('users').insertOne({ username, password });
    return new Response(JSON.stringify({ username }), { status: 201 });
  } catch (e) {
    return new Response(JSON.stringify({ message: 'Server error' }), { status: 500 });
  }
}
