// GET /api/user/[username]
export default function handler(req, res) {
  // Implement user-specific data fetching
  res.status(200).json({ message: 'User endpoint' });
}
