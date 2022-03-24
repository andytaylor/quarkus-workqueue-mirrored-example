import React from 'react'

const Stocks = ({ stocks }) => {
    console.log(stocks)
    return (
        <div className="pf-c-page">
          <header className="pf-c-page__header">
            <div className="pf-c-page__header-brand">
              <a href="#" className="pf-c-page__header-brand-link">Stock Status</a>
            </div>
            <div className="pf-c-page__header-nav">stock</div>
            <div className="pf-c-page__header-tools">settings</div>
          </header>
          <main className="pf-c-page__main" tabIndex="-1">
            <section className="pf-c-page__main-section pf-m-light">
            <table className="pf-c-table pf-m-grid-md" role="grid" aria-label="Current Stock Status" id="table-basic">
                <caption>Stock</caption>
                <thead>
                    <tr role="row">
                        <th role="columnheader" scope="col">Name</th>
                        <th role="columnheader" scope="col">Quantity</th>
                        <th role="columnheader" scope="col">Price</th>
                    </tr>
                </thead>
                <tbody>
                {
                    stocks.map((stock) => (
                        <tr key={stock.name} role="row">
                            <td role="cell" data-label="name">{stock.name}</td>
                            <td role="cell" data-label="quantity">{stock.quantity}</td>
                            <td role="cell" data-label="price">{stock.price}</td>
                        </tr>
                ))}
                </tbody>
            </table>
            </section>
            <section className="pf-c-page__main-section"></section>
          </main>
        </div>
    )
};

export default Stocks