// Optional sample data for MongoDB.
// Run in mongosh:   load("sample-data.js")
// Or paste into the MONGOSH panel at the bottom of MongoDB Compass.

db = db.getSiblingDB("fitzonedb");

db.workouts.deleteMany({});
db.members.deleteMany({});

const m1 = ObjectId("65f000000000000000000001");
const m2 = ObjectId("65f000000000000000000002");
const m3 = ObjectId("65f000000000000000000003");

db.members.insertMany([
  { _id: m1, name: "Ramesh Thapa", email: "ramesh@example.com", age: 28, plan: "BASIC",    joinDate: new Date("2026-08-01"), active: true,  _class: "com.fitzone.tracker.entity.Member" },
  { _id: m2, name: "Anita Sharma", email: "anita@example.com",  age: 22, plan: "PREMIUM",  joinDate: new Date("2026-09-10"), active: true,  _class: "com.fitzone.tracker.entity.Member" },
  { _id: m3, name: "Bikash Rai",   email: "bikash@example.com", age: 35, plan: "STANDARD", joinDate: new Date("2026-07-15"), active: false, _class: "com.fitzone.tracker.entity.Member" }
]);

db.workouts.insertMany([
  { memberId: m1.toString(), exerciseType: "Cardio",   durationMinutes: 40, caloriesBurned: 320, workoutDate: new Date("2026-10-10"), notes: "Treadmill",      _class: "com.fitzone.tracker.entity.Workout" },
  { memberId: m1.toString(), exerciseType: "Strength", durationMinutes: 55, caloriesBurned: 400, workoutDate: new Date("2026-10-12"), notes: "Leg day",        _class: "com.fitzone.tracker.entity.Workout" },
  { memberId: m2.toString(), exerciseType: "Yoga",     durationMinutes: 60, caloriesBurned: 210, workoutDate: new Date("2026-10-11"), notes: "Morning session", _class: "com.fitzone.tracker.entity.Workout" },
  { memberId: m2.toString(), exerciseType: "Cardio",   durationMinutes: 30, caloriesBurned: 280, workoutDate: new Date("2026-10-13"), notes: "Cycling",        _class: "com.fitzone.tracker.entity.Workout" },
  { memberId: m3.toString(), exerciseType: "Strength", durationMinutes: 50, caloriesBurned: 360, workoutDate: new Date("2026-10-09"), notes: "Chest and back",  _class: "com.fitzone.tracker.entity.Workout" }
]);

print("Members: " + db.members.countDocuments() + ", Workouts: " + db.workouts.countDocuments());
