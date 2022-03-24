import React, {Component} from 'react';
import Stocks from './components/stocks'

class App extends Component {
  state = {
    stocks: []
  }

  componentDidMount() {
    fetch('/stock/all')
    .then(res => res.json())
    .then((data) => {
      console.log(data)
      this.setState({ stocks: data })
    })
    .catch(console.log)
  }

  render () {
    return (
      <Stocks stocks={this.state.stocks} />
    );
  }
}

export default App;