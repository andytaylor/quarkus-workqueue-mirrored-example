import React from 'react'
import { Button, Modal, ModalVariant, Wizard, Form, FormGroup, TextInput, WizardFooter, WizardContextConsumer } from '@patternfly/react-core';

const Stocks = ({ stocks }) => {
    console.log(stocks)

    const handleQuantityChange = value => {
        setQuantity(value)
    }

    const handlePriceChange = value => {
        setPrice(value)
    }

    const handleNameChange = value => {
        setName(value)
    }

    const onClose = () => {
        setIsWizardOpen(false)
        console.log("closing wizard")
    }

    const openWizard = (name, quantity, price, isCreateNew) => {
        setName(name)
        setQuantity(quantity)
        setPrice(price)
        setIsCreateNew(isCreateNew)
        setIsWizardOpen(true)
    }

    const validate = onNext => {
        const object = { name: name, price: price , quantity: quantity };
        fetch('/stock/update', {
            headers: {
                'Accept': 'application/json',
                'Content-type': 'application/json'
            },
            method: 'PUT',
            body: JSON.stringify(object)
        })
            .then(res => res.json())
            .then((data) => {
            console.log(data)
        })
        .catch(console.log)
        window.location.reload(false);
        setIsWizardOpen(false)
        console.log("submitting wizard")
    }

    const [isWizardOpen, setIsWizardOpen] = React.useState(false);
    const [isCreateNew, setIsCreateNew] = React.useState(false);
    const [ name, setName ] = React.useState("");
    const [ quantity, setQuantity ] = React.useState(0);
    const [ price, setPrice ] = React.useState(0.00);


    const CustomFooter = (
        <WizardFooter>
          <WizardContextConsumer>
            {({ activeStep, goToStepByName, goToStepById, onNext, onBack, onClose }) => {
                return (
                  <>
                    <Button variant="primary" type="submit" onClick={() => validate(onNext) }>Submit</Button>
                    <Button variant="secondary" onClick={onBack} className={activeStep.name === 'Enter Stock Data' ? 'pf-m-disabled' : ''}>
                      Back
                    </Button>
                    <Button variant="link" onClick={onClose}>
                      Cancel
                    </Button>
                  </>
                )
              }}
          </WizardContextConsumer>
       </WizardFooter>
    );

    const steps = [
          { id: 0, name: 'Enter Stock Data:', component:
           <Form isHorizontal>
                <b>{name}</b>
                { isCreateNew && (
                <FormGroup label="Name" isRequired fieldId="horizontal-form-name">
                  <TextInput
                    value={name}
                    isRequired
                    type="text"
                    id="horizontal-form-name"
                    aria-describedby="horizontal-form-name-helper"
                    name="horizontal-form-name"
                    onChange={handleNameChange}/>
                </FormGroup>)
                }
                <FormGroup label="Quantity" isRequired fieldId="horizontal-form-name">
                  <TextInput
                    value={quantity}
                    isRequired
                    type="text"
                    id="horizontal-form-name"
                    aria-describedby="horizontal-form-name-helper"
                    name="horizontal-form-name"
                    onChange={handleQuantityChange}
                  />
                </FormGroup>
                <FormGroup label="Price" isRequired fieldId="horizontal-form-name">
                  <TextInput
                    value={price}
                    isRequired
                    type="text"
                    id="horizontal-form-name"
                    aria-describedby="horizontal-form-name-helper"
                    name="horizontal-form-name"
                    onChange={handlePriceChange}
                  />
                </FormGroup>
            </Form>
            }
        ];

    return (
        <div className="pf-c-page">
          <header className="pf-c-page__header">
            <div className="pf-c-page__header-brand">Acme Warehouse</div>
            <div className="pf-c-page__header-nav">
                <a href="#" className="pf-c-page__header-brand-link">Stock Status</a>
            </div>
            <div className="pf-c-page__header-tools">settings</div>
          </header>
          <main className="pf-c-page__main" tabIndex="-1">
            <section className="pf-c-page__main-section pf-m-light">
            <table className="pf-c-table pf-m-grid-md" role="grid" aria-label="Current Stock Status" id="table-basic">
                <caption>Stock      <Button variant="primary" onClick={() => openWizard("name", 0.00, 0, true)}>New</Button></caption>
                <thead>
                    <tr role="row">
                        <th role="columnheader" scope="col">Name</th>
                        <th role="columnheader" scope="col">Quantity</th>
                        <th role="columnheader" scope="col">Price</th>
                        <th role="columnHeader" scope="col">Action</th>
                    </tr>
                </thead>
                <tbody>
                {
                    stocks.map((stock) => (
                        <tr key={stock.name} role="row">
                            <td role="cell" data-label="name">{stock.name}</td>
                            <td role="cell" data-label="quantity">{stock.quantity}</td>
                            <td role="cell" data-label="price">{formatter.format(stock.price)}</td>
                            <td role="cell" data-label="action">
                                <Button variant="primary" onClick={() => openWizard(stock.name, stock.quantity, stock.price, false)}>Update</Button>
                            </td>
                        </tr>
                ))}
                </tbody>
            </table>
            { isWizardOpen && (
                  <Modal
                    isOpen
                    variant={ModalVariant.large}
                    showClose={false}
                    hasNoBodyWrapper
                    aria-describedby="wiz-modal-demo-description"
                    aria-labelledby="wiz-modal-demo-title"
                  >
                    <Wizard
                      navAriaLabel={`Stock Update`}
                      mainAriaLabel={`Stock Update`}
                      titleId="wiz-modal-demo-title"
                      descriptionId="wiz-modal-demo-description"
                      title="Update Stock"
                      description="Enter Stock Quantity and Price"
                      steps={steps}
                      height={400}
                      onClose={onClose}
                      footer={CustomFooter}
                    />
                  </Modal>)
            }
            </section>
            <section className="pf-c-page__main-section"></section>
          </main>
        </div>
    )
};

// Create our number formatter.
var formatter = new Intl.NumberFormat('en-US', {
  style: 'currency',
  currency: 'USD',
});

export default Stocks