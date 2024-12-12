import React, { useState, useEffect } from 'react';
import './LogDisplay.css'; // Import the stylesheet

const LogDisplay = () => {
  const [logs, setLogs] = useState([]); // State to store logs

  // Function to fetch logs from the backend
  const fetchLogs = async () => {
    try {
      const response = await fetch('http://localhost:8080/api/logs')
      if (response.ok) {
        const data = await response.json();
        setLogs(data); // Update state with fetched logs
      } else {
        console.error('Failed to fetch logs');
      }
    } catch (error) {
      console.error('Error fetching logs:', error);
    }
  };

  // Periodically fetch logs
  useEffect(() => {
    fetchLogs(); // Fetch logs immediately
    const interval = setInterval(fetchLogs, 2000); // Fetch logs every 2 seconds
    return () => clearInterval(interval); // Cleanup interval on component unmount
  }, []);

  return (
    <div className="log-display">
      <h3 className="log-title">System Logs</h3>
      <div className="log-container">
        <ul className="log-list">
          {logs.map((log, index) => (
            <li key={index}>{log}</li> // Display each log as a list item
          ))}
        </ul>
      </div>
    </div>
  );
};

export default LogDisplay;
