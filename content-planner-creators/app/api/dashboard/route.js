import clientPromise from '../../../lib/mongodb';

export async function GET(request) {
  const { searchParams } = new URL(request.url);
  const username = searchParams.get('username');
  if (!username) {
    return new Response(JSON.stringify({ message: 'Missing username' }), { status: 400 });
  }
  try {
    const client = await clientPromise;
    const db = client.db();
    const user = await db.collection('users').findOne({ username });
    if (!user) {
      return new Response(JSON.stringify({ message: 'User not found' }), { status: 404 });
    }
    const challenges = await db.collection('challenges').find({ userId: user._id }).toArray();
    return new Response(JSON.stringify({ challenges }), { status: 200 });
  } catch (e) {
    return new Response(JSON.stringify({ message: 'Server error' }), { status: 500 });
  }
}
