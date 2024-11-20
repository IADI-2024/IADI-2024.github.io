import './App.css';
import Counter from './components/Counter';
import BooksList, { BookCounter, SearchBox } from './components/Books';
import { Provider, useSelector } from 'react-redux'
import { GlobalState, store } from './store';

function App() {

  const counter = useSelector((state:GlobalState) => state.counter)

  return (
   <div>
    <SearchBox/>
    <Counter/>
    <BookCounter/>
    <BooksList/>
    </div>
  );
}

const RdxApp = () => 
  <Provider store={store}>
    <App/>
  </Provider>

export default RdxApp;
