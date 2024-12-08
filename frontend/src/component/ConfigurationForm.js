import React from 'react';
import './ConfigurationForm.css'; // Import the stylesheet

const ConfigurationForm = () => {
  return (
    <div className="configuration-form">
      <h2>System Configuration</h2>
      <form>
        <div className="form-group">
          <label htmlFor="totalTickets">Total Number of Tickets</label>
          <input type="input" id="totalTickets" placeholder="Enter total tickets" />
        </div>
        <div className="form-group">
          <label htmlFor="ticketReleaseRate">Ticket Release Rate</label>
          <input type="input" id="ticketReleaseRate" placeholder="Enter release rate" />
        </div>
        <div className="form-group">
          <label htmlFor="customerRetrievalRate">Customer Retrieval Rate</label>
          <input type="input" id="customerRetrievalRate" placeholder="Enter retrieval rate" />
        </div>
        <div className="form-group">
          <label htmlFor="maxTicketCapacity">Maximum Ticket Capacity</label>
          <input type="input" id="maxTicketCapacity" placeholder="Enter max capacity" />
        </div>
        <div className="form-actions">
          <button type="button">Save</button>
          <button type="button">Show</button>
          <button type="reset">Reset</button>

        </div>
      </form>
    </div>
  );
};

export default ConfigurationForm;
