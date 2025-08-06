const express = require('express');
const router = express.Router();
const User = require('../models/User');

// Signup
router.post('/signup', async (req, res) => {
  try {
    const { name, rollnumber, email, phone, password, roomno } = req.body;
    if (!name || !rollnumber || !email || !phone || !password || !roomno) {
      return res.status(400).json({ message: 'All fields are required' });
    }
    const existing = await User.findOne({ rollnumber });
    if (existing) {
      return res.status(409).json({ message: 'Roll number already exists' });
    }
    const user = new User({ name, rollnumber, email, phone, password, roomno, isAdmin: false });
    await user.save();
    res.status(201).json({ message: 'User registered', user: { rollnumber, name, email, phone, roomno, isAdmin: false } });
  } catch (err) {
    res.status(500).json({ message: 'Server error', error: err.message });
  }
});

// Login
router.post('/login', async (req, res) => {
  try {
    const { rollnumber, password } = req.body;
    if (!rollnumber || !password) {
      return res.status(400).json({ message: 'Roll number and password required' });
    }
    const user = await User.findOne({ rollnumber });
    if (!user || user.password !== password) {
      return res.status(401).json({ message: 'Invalid roll number or password' });
    }
    res.json({ message: 'Login successful', user: { rollnumber: user.rollnumber, name: user.name, email: user.email, phone: user.phone, roomno: user.roomno, isAdmin: user.isAdmin } });
  } catch (err) {
    res.status(500).json({ message: 'Server error', error: err.message });
  }
});

module.exports = router;
