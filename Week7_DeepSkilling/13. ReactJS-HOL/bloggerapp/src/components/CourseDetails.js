import React, { useState } from 'react';

const CourseDetails = ({ userRole }) => {
  const [enrolledCourses, setEnrolledCourses] = useState(new Set([1]));
  const [selectedDifficulty, setSelectedDifficulty] = useState('all');
  const [showCurriculum, setShowCurriculum] = useState({});

  const courses = [
    {
      id: 1,
      title: "React Fundamentals",
      instructor: "Sarah Connor",
      difficulty: "beginner",
      duration: "8 weeks",
      price: 99,
      rating: 4.8,
      students: 1240,
      free: false,
      completed: true,
      progress: 100,
      description: "Master the basics of React including components, props, state, and event handling.",
      curriculum: ["Introduction to React", "Components & JSX", "Props & State", "Event Handling", "Project: Todo App"]
    },
    {
      id: 2,
      title: "Advanced React Patterns",
      instructor: "John Connor",
      difficulty: "advanced",
      duration: "12 weeks",
      price: 199,
      rating: 4.9,
      students: 856,
      free: false,
      completed: false,
      progress: 0,
      description: "Deep dive into advanced React patterns, performance optimization, and testing strategies.",
      curriculum: ["Higher-Order Components", "Render Props", "Context API", "Performance Optimization", "Testing"]
    },
    {
      id: 3,
      title: "JavaScript Basics",
      instructor: "Alice Wonder",
      difficulty: "beginner",
      duration: "6 weeks",
      price: 0,
      rating: 4.5,
      students: 2100,
      free: true,
      completed: false,
      progress: 0,
      description: "Learn JavaScript fundamentals from variables to functions and objects.",
      curriculum: ["Variables & Data Types", "Functions", "Objects & Arrays", "DOM Manipulation", "ES6 Features"]
    },
    {
      id: 4,
      title: "Full Stack Development",
      instructor: "Bob Builder",
      difficulty: "intermediate",
      duration: "16 weeks",
      price: 299,
      rating: 4.7,
      students: 645,
      free: false,
      completed: false,
      progress: 0,
      description: "Complete full stack development course covering frontend, backend, and database.",
      curriculum: ["Frontend Setup", "Backend APIs", "Database Design", "Authentication", "Deployment"]
    }
  ];

  // Method 1: Filter function with multiple conditions
  const getFilteredCourses = () => {
    let filtered = courses;
    
    if (selectedDifficulty !== 'all') {
      filtered = filtered.filter(course => course.difficulty === selectedDifficulty);
    }
    
    // Guest users only see free courses
    if (userRole === 'guest') {
      filtered = filtered.filter(course => course.free);
    }
    
    return filtered;
  };

  const enrollInCourse = (courseId) => {
    if (userRole !== 'guest') {
      setEnrolledCourses(prev => new Set([...prev, courseId]));
    }
  };

  const toggleCurriculum = (courseId) => {
    setShowCurriculum(prev => ({
      ...prev,
      [courseId]: !prev[courseId]
    }));
  };

  // Method 2: Function for enrollment status
  const getEnrollmentStatus = (course) => {
    if (userRole === 'guest') {
      return course.free ? 'Available' : 'Login Required';
    } else if (enrolledCourses.has(course.id)) {
      return course.completed ? 'Completed' : 'In Progress';
    } else {
      return 'Not Enrolled';
    }
  };

  return (
    <div className="course-details">
      <h2>🎓 Course Details Component</h2>
      
      {/* Method 3: Ternary for user status */}
      <div className="user-dashboard">
        {userRole === 'guest' ? (
          <div className="guest-info">
            <h3>👋 Welcome Guest!</h3>
            <p>Sign up to access premium courses and track your progress.</p>
            <button className="signup-btn">Sign Up Now</button>
          </div>
        ) : (
          <div className="user-info">
            <h3>📊 Your Learning Dashboard</h3>
            <div className="stats">
              <span>Enrolled: {enrolledCourses.size}</span>
              <span>Completed: {courses.filter(c => enrolledCourses.has(c.id) && c.completed).length}</span>
              <span>In Progress: {courses.filter(c => enrolledCourses.has(c.id) && !c.completed).length}</span>
            </div>
          </div>
        )}
      </div>

      {/* Difficulty Filter */}
      <div className="filter-section">
        <h3>Filter by Difficulty:</h3>
        {['all', 'beginner', 'intermediate', 'advanced'].map(level => (
          <button
            key={level}
            onClick={() => setSelectedDifficulty(level)}
            className={selectedDifficulty === level ? 'active' : ''}
          >
            {level.charAt(0).toUpperCase() + level.slice(1)}
          </button>
        ))}
      </div>

      {/* Method 4: Logical AND for admin features */}
      {userRole === 'admin' && (
        <div className="admin-course-panel">
          <h3>🔧 Course Management</h3>
          <div className="admin-course-stats">
            <span>Total Courses: {courses.length}</span>
            <span>Free Courses: {courses.filter(c => c.free).length}</span>
            <span>Premium Courses: {courses.filter(c => !c.free).length}</span>
          </div>
          <div className="admin-actions">
            <button>Add New Course</button>
            <button>Manage Instructors</button>
            <button>View Analytics</button>
          </div>
        </div>
      )}

      <div className="courses-grid">
        {/* Method 5: Complex conditional with map */}
        {getFilteredCourses().length > 0 ? (
          getFilteredCourses().map(course => (
            <div key={course.id} className="course-card">
              <div className="course-header">
                <h3>{course.title}</h3>
                
                {/* Method 6: Multiple badge conditions */}
                <div className="course-badges">
                  {course.free && <span className="badge free">Free</span>}
                  {course.rating >= 4.8 && <span className="badge top-rated">⭐ Top Rated</span>}
                  {course.students > 1000 && <span className="badge popular">🔥 Popular</span>}
                  {enrolledCourses.has(course.id) && <span className="badge enrolled">✓ Enrolled</span>}
                </div>
              </div>
              
              <div className="course-meta">
                <p><strong>Instructor:</strong> {course.instructor}</p>
                <p><strong>Duration:</strong> {course.duration}</p>
                <p><strong>Difficulty:</strong> 
                  <span className={`difficulty ${course.difficulty}`}>
                    {course.difficulty.charAt(0).toUpperCase() + course.difficulty.slice(1)}
                  </span>
                </p>
                <p><strong>Students:</strong> {course.students.toLocaleString()}</p>
                <p><strong>Rating:</strong> {course.rating}/5.0</p>
              </div>
              
              <div className="course-description">
                <p>{course.description}</p>
              </div>
              
              {/* Method 7: Price display with user role conditions */}
              <div className="course-price">
                {course.free ? (
                  <span className="price free-price">FREE</span>
                ) : userRole === 'guest' ? (
                  <span className="price">Login to see price</span>
                ) : (
                  <span className="price">${course.price}</span>
                )}
              </div>
              
              {/* Method 8: Progress bar for enrolled courses */}
              {enrolledCourses.has(course.id) && userRole !== 'guest' && (
                <div className="progress-section">
                  <div className="progress-info">
                    <span>Progress: {course.progress}%</span>
                    <span className="status">{course.completed ? '✅ Completed' : '📚 In Progress'}</span>
                  </div>
                  <div className="progress-bar">
                    <div 
                      className="progress-fill" 
                      style={{width: `${course.progress}%`}}
                    ></div>
                  </div>
                </div>
              )}
              
              {/* Method 9: Enrollment status */}
              <div className="enrollment-status">
                <span className="status-text">
                  Status: {getEnrollmentStatus(course)}
                </span>
              </div>
              
              <div className="course-actions">
                {/* Method 10: Complex button logic with switch-like ternary */}
                {(() => {
                  if (userRole === 'guest') {
                    return course.free ? (
                      <button className="enroll-btn">Start Free Course</button>
                    ) : (
                      <button className="login-btn">Login to Enroll</button>
                    );
                  } else if (enrolledCourses.has(course.id)) {
                    return course.completed ? (
                      <button className="completed-btn" disabled>✅ Completed</button>
                    ) : (
                      <button className="continue-btn">Continue Learning</button>
                    );
                  } else {
                    return (
                      <button 
                        className="enroll-btn"
                        onClick={() => enrollInCourse(course.id)}
                      >
                        {course.free ? 'Enroll for Free' : `Enroll for $${course.price}`}
                      </button>
                    );
                  }
                })()}
                
                <button 
                  className="curriculum-btn"
                  onClick={() => toggleCurriculum(course.id)}
                >
                  {showCurriculum[course.id] ? 'Hide' : 'Show'} Curriculum
                </button>
                
                {/* Method 11: Admin-only actions */}
                {userRole === 'admin' && (
                  <div className="admin-course-actions">
                    <button className="admin-btn">Edit Course</button>
                    <button className="admin-btn">View Analytics</button>
                  </div>
                )}
              </div>
              
              {/* Method 12: Curriculum display with conditional rendering */}
              {showCurriculum[course.id] && (
                <div className="curriculum">
                  <h4>📋 Course Curriculum</h4>
                  {course.curriculum.map((topic, index) => (
                    <div key={index} className="curriculum-item">
                      <span className="lesson-number">{index + 1}.</span>
                      <span className="lesson-title">{topic}</span>
                      {/* Method 13: Conditional check marks for enrolled users */}
                      {enrolledCourses.has(course.id) && course.progress > (index * 20) && (
                        <span className="completed-lesson">✅</span>
                      )}
                    </div>
                  ))}
                </div>
              )}
            </div>
          ))
        ) : (
          <div className="no-courses">
            <h3>No courses found</h3>
            <p>
              {selectedDifficulty === 'all' 
                ? (userRole === 'guest' 
                    ? 'No free courses available. Please login to see premium courses.' 
                    : 'No courses available.')
                : `No ${selectedDifficulty} level courses found.`
              }
            </p>
          </div>
        )}
      </div>
    </div>
  );
};

export default CourseDetails;