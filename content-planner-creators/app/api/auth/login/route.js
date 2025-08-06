// POST /api/auth/login
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
    const user = await db.collection('users').findOne({ username });
    if (!user) {
      return new Response(JSON.stringify({ message: 'Invalid username or password' }), { status: 401 });
    }
    // For demo: compare as plain text
    if (password !== user.password) {
      return new Response(JSON.stringify({ message: 'Invalid username or password' }), { status: 401 });
    }
    // On success, return username (could set cookie/JWT)
    return new Response(JSON.stringify({ username }), { status: 200 });
  } catch (e) {
    return new Response(JSON.stringify({ message: 'Server error' }), { status: 500 });
  }
}
