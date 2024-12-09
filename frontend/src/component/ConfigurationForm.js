// import React from 'react';
// import './ConfigurationForm.css'; // Import the stylesheet

// const ConfigurationForm = () => {
//   return (
//     <div className="configuration-form">
//       <h2>System Configuration</h2>
//       <form>
//         <div className="form-group">
//           <label htmlFor="totalTickets">Total Number of Tickets</label>
//           <input type="input" id="totalTickets" placeholder="Enter total tickets" />
//         </div>
//         <div className="form-group">
//           <label htmlFor="ticketReleaseRate">Ticket Release Rate</label>
//           <input type="input" id="ticketReleaseRate" placeholder="Enter release rate" />
//         </div>
//         <div className="form-group">
//           <label htmlFor="customerRetrievalRate">Customer Retrieval Rate</label>
//           <input type="input" id="customerRetrievalRate" placeholder="Enter retrieval rate" />
//         </div>
//         <div className="form-group">
//           <label htmlFor="maxTicketCapacity">Maximum Ticket Capacity</label>
//           <input type="input" id="maxTicketCapacity" placeholder="Enter max capacity" />
//         </div>
//         <div className="form-actions">
//           <button type="button">Save</button>
//           <button type="button">Show</button>
//           <button type="reset">Reset</button>

//         </div>
//       </form>
//     </div>
//   );
// };

// export default ConfigurationForm;



import React, { useState } from 'react';
import './ConfigurationForm.css'; // Import the stylesheet

const ConfigurationForm = () => {
  const [formData, setFormData] = useState({
    totalTickets: '',
    ticketReleaseRate: '',
    customerRetrievalRate: '',
    maxTicketCapacity: '',
  });

  const [message, setMessage] = useState('');
  const [fetchedConfig, setFetchedConfig] = useState(null);

  // Handle input change
  const handleChange = (e) => {
    const { id, value } = e.target;
    setFormData((prevData) => ({
      ...prevData,
      [id]: value,
    }));
  };

  // Handle Save (POST request)
  const handleSave = async () => {
    try {
      const response = await fetch('http://localhost:8080/api/config', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(formData),
      });
      if (response.ok) {
        const data = await response.text();
        setMessage(`Configuration saved successfully: ${data}`);
      } else {
        const errorText = await response.text();
        setMessage(`Error saving configuration: ${errorText}`);
      }
    } catch (error) {
      setMessage(`Error: ${error.message}`);
    }
  };

  // Handle Show (GET request)
  const handleShow = async () => {
    try {
      const response = await fetch('http://localhost:8080/api/config', {
        method: 'GET',
      });
      if (response.ok) {
        const data = await response.json();
        setFetchedConfig(data);
        setMessage('Configuration fetched successfully!');
      } else {
        setMessage('Error fetching configuration.');
      }
    } catch (error) {
      setMessage(`Error: ${error.message}`);
    }
  };

  // Handle Reset
  const handleReset = () => {
    setFormData({
      totalTickets: '',
      ticketReleaseRate: '',
      customerRetrievalRate: '',
      maxTicketCapacity: '',
    });
    setFetchedConfig(null);
    setMessage('');
  };

  return (
    <div className="configuration-form">
      <h2>System Configuration</h2>
      <form>
        <div className="form-group">
          <label htmlFor="totalTickets">Total Number of Tickets</label>
          <input
            type="input"
            id="totalTickets"
            value={formData.totalTickets}
            onChange={handleChange}
            placeholder="Enter total tickets"
          />
        </div>
        <div className="form-group">
          <label htmlFor="ticketReleaseRate">Ticket Release Rate</label>
          <input
            type="input"
            id="ticketReleaseRate"
            value={formData.ticketReleaseRate}
            onChange={handleChange}
            placeholder="Enter release rate"
          />
        </div>
        <div className="form-group">
          <label htmlFor="customerRetrievalRate">Customer Retrieval Rate</label>
          <input
            type="input"
            id="customerRetrievalRate"
            value={formData.customerRetrievalRate}
            onChange={handleChange}
            placeholder="Enter retrieval rate"
          />
        </div>
        <div className="form-group">
          <label htmlFor="maxTicketCapacity">Maximum Ticket Capacity</label>
          <input
            type="input"
            id="maxTicketCapacity"
            value={formData.maxTicketCapacity}
            onChange={handleChange}
            placeholder="Enter max capacity"
          />
        </div>
        <div className="form-actions">
          <button type="button" onClick={handleSave}>
            Save
          </button>
          <button type="button" onClick={handleShow}>
            Show
          </button>
          <button type="reset" onClick={handleReset}>
            Reset
          </button>
        </div>
      </form>
      <div className="status-message">
        <p>{message}</p>
        {fetchedConfig && (
          <div className="config-display">
            <h3>Fetched Configuration</h3>
            <p>Total Tickets: {fetchedConfig.totalTickets}</p>
            <p>Ticket Release Rate: {fetchedConfig.ticketReleaseRate}</p>
            <p>Customer Retrieval Rate: {fetchedConfig.customerRetrievalRate}</p>
            <p>Max Ticket Capacity: {fetchedConfig.maxTicketCapacity}</p>
          </div>
        )}
      </div>
    </div>
  );
};

export default ConfigurationForm;
