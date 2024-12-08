import React from 'react';
import './TicketDisplay.css'; // Import the stylesheet

const TicketDisplay = () => {
  return (
    <div className="ticket-display">
      <h3 className="ticket-title">Current Ticket Pool</h3>
      <div className="ticket-status">Total Tickets Available: </div>
    </div>
  );
};

export default TicketDisplay;
