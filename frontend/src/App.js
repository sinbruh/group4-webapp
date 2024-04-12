import React from 'react';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
import About from './About';
import Contact from './Contact';
import Index from './Index';
import Search from './Search';

function App() {
    return (
        <Router>
        <Switch>
            <Route path="/about" component={About} />
            <Route path="/contact" component={Contact} />
            <Route path="/search" component={Search} />
            <Route path="/" component={Index} />
        </Switch>
        </Router>
    );
}

export default App;