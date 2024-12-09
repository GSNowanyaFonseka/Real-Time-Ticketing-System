import React from 'react';
import './Header.css'; // Import the stylesheet
import headerImg from '../assets/headerImg.png';

const Header = () => {
  return (
    <div className="header-container">
      <div className="header-content">
        <div className="header-text">
          <h1 className="header-title">Ticket Management System</h1>
          <p className="header-subtitle">Welcome! Configure your system settings below to get started.</p>
        </div>
        <div className="header-image">
          <img src={headerImg} alt="System Illustration" />
        </div>
      </div>
    </div>
  );
};

export default Header;
