import './App.css';
import Counter from './components/Counter';
import BooksList from './components/Books';
import { Provider } from 'react-redux'
import { store } from './stores';
import { SearchBox } from './components/Books/SearchBox';

function App() {
  return (
   <div>
    <Counter/>
    <SearchBox/>
    <BooksList/>
    </div>
  );
}

const RdxApp = () => 
  <Provider store={store}>
    <App/>
  </Provider>

export default RdxApp;
