import mongoose from 'mongoose';

const daySchema = new mongoose.Schema({
  title: String,
  imagePrompt: String,
  hashtags: [String],
  posted: { type: Boolean, default: false }
});

const challengeSchema = new mongoose.Schema({
  userId: { type: mongoose.Schema.Types.ObjectId, ref: 'User' },
  challengeType: String, // Reels, Shorts, Blog
  days: [daySchema],
  createdAt: { type: Date, default: Date.now }
});

export default mongoose.models.Challenge || mongoose.model('Challenge', challengeSchema);
