import React, { useState } from 'react';
import './ControlPanel.css'; // Import the stylesheet

const ControlPanel = () => {
  const [status, setStatus] = useState(''); // State to display system status

  // Function to handle system start
  const handleStart = async () => {
    try {
      const response = await fetch('http://localhost:8080/api/config/start', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
      });
      if (response.ok) {
        const data = await response.text(); // Assuming backend sends a string response
        setStatus(`System started: ${data}`);
      } else {
        setStatus('Failed to start the system.');
      }
    } catch (error) {
      setStatus('Error starting the system: ' + error.message);
    }
  };

  // Function to handle system stop
  const handleStop = async () => {
    try {
      const response = await fetch('http://localhost:8080/api/config/stop', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
      });
      if (response.ok) {
        const data = await response.text(); // Assuming backend sends a string response
        setStatus(`System stopped: ${data}`);
      } else {
        setStatus('Failed to stop the system.');
      }
    } catch (error) {
      setStatus('Error stopping the system: ' + error.message);
    }
  };

  return (
    <div className="control-panel">
      <h3 className="control-title">System Control Panel</h3>
      <div className="button-container">
        <button className="control-btn start-btn" onClick={handleStart}>
          Start
        </button>
        <button className="control-btn stop-btn" onClick={handleStop}>
          Stop
        </button>
      </div>
      <div className="status-message">
        <p>{status}</p>
      </div>
    </div>
  );
};

export default ControlPanel;

