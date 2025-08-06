import { ObjectId } from 'mongodb';
import clientPromise from '../../../../lib/mongodb';

// GET /api/challenge/[id]
export async function GET(request, { params }) {
  const { id } = params;
  try {
    const client = await clientPromise;
    const db = client.db();
    const challenge = await db.collection('challenges').findOne({ _id: new ObjectId(id) });
    if (!challenge) {
      return new Response(JSON.stringify({ message: 'Challenge not found' }), { status: 404 });
    }
    return new Response(JSON.stringify({ challenge }), { status: 200 });
  } catch (e) {
    return new Response(JSON.stringify({ message: 'Server error' }), { status: 500 });
  }
}

// PUT /api/challenge/[id]
export async function PUT(request, { params }) {
  const { id } = params;
  try {
    const body = await request.json();
    // Edit mode: update challengeType and days
    if (body.days && Array.isArray(body.days)) {
      const update = {};
      if (body.challengeType) update.challengeType = body.challengeType;
      update.days = body.days;
      const client = await clientPromise;
      const db = client.db();
      const result = await db.collection('challenges').updateOne(
        { _id: new ObjectId(id) },
        { $set: update }
      );
      if (result.matchedCount === 0) {
        return new Response(JSON.stringify({ message: 'Challenge not found' }), { status: 404 });
      }
      return new Response(JSON.stringify({ success: true }), { status: 200 });
    }
    // Single day posted update (backward compatible)
    const { dayIdx, posted } = body;
    if (typeof dayIdx === 'number' && typeof posted === 'boolean') {
      const client = await clientPromise;
      const db = client.db();
      const update = {};
      update[`days.${dayIdx}.posted`] = posted;
      const result = await db.collection('challenges').updateOne(
        { _id: new ObjectId(id) },
        { $set: update }
      );
      if (result.matchedCount === 0) {
        return new Response(JSON.stringify({ message: 'Challenge not found' }), { status: 404 });
      }
      return new Response(JSON.stringify({ success: true }), { status: 200 });
    }
    return new Response(JSON.stringify({ message: 'Invalid input' }), { status: 400 });
  } catch (e) {
    return new Response(JSON.stringify({ message: 'Server error' }), { status: 500 });
  }
}

// DELETE /api/challenge/[id]
export async function DELETE(request, { params }) {
  const { id } = params;
  try {
    const client = await clientPromise;
    const db = client.db();

    const result = await db.collection('challenges').deleteOne({ _id: new ObjectId(id) });

    if (result.deletedCount === 0) {
      return new Response(JSON.stringify({ message: 'Challenge not found' }), { status: 404 });
    }

    return new Response(JSON.stringify({ message: 'Challenge deleted' }), { status: 200 });
  } catch (e) {
    return new Response(JSON.stringify({ message: 'Server error' }), { status: 500 });
  }
}
