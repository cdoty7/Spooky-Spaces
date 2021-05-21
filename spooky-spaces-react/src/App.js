import {
  BrowserRouter as Router,
  Switch,
  Route,
} from "react-router-dom";
import Home from "./components/Home";
import About from "./components/static/About";
import NotFound from "./components/static/NotFound";

function App() {
  return (
    <div className="App">
        <Router>
          <Switch>
            <Route exact path="/">
              <Home />
            </Route>
            <Route path="/about">
              <About />
            </Route>
            <Route path="/allspaces">
            
            </Route>
            <Route path="*">
            <NotFound />
            </Route>
          </Switch>
        </Router>
    </div>
  );
}

export default App;
