import './App.css';
import ConfigurationForm from './component/ConfigurationForm';
import ControlPanel from './component/ControlPanel';
import Header from './component/Header';
import LogDisplay from './component/LogDisplay';
import Footer from './component/Footer';

function App() {
  
  return (
    <div className="App">
      <Header/>
      <ConfigurationForm/>
      <ControlPanel/>
      <LogDisplay/>
      <Footer/>
    </div>
  );
}

export default App;
