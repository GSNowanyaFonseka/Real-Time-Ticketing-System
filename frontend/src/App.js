import './App.css';
import ConfigurationForm from './component/ConfigurationForm';
import ControlPanel from './component/ControlPanel';
import Header from './component/Header';
import TicketDisplay from './component/TicketDisplay';

function App() {
  
  return (
    <div className="App">
      <Header/>
      <ConfigurationForm/>
      <ControlPanel/>
      <TicketDisplay/>
    </div>
  );
}

export default App;
