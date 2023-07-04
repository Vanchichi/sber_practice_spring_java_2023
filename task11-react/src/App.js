
import React, {useState} from 'react';
import './App.css';
import {Products} from "./components/Products";
import {Profile} from "./components/Profile";
import {Cart} from "./components/Cart";

function App() {
    const [currentClient, setCurrentUser] = useState(null);
    const [cartItems, setCartItems] = useState([]);

    const handleUserChange = (user) => {
        setCurrentUser(user);
        setCartItems([]);
    };
    const handleAddToCart = (product, quantity) => {
        const existingItem = cartItems.find((item) => item.product.id === product.id);
        if (existingItem) {
            const updatedItem = {
                ...existingItem,
                quantity: existingItem.quantity + quantity,
            };
            const newItem = {
                product,
                quantity,
            };
            setCartItems([...cartItems, newItem]);
        }
    };

    const handleUpdateQuantity = (productId, newQuantity) => {
        const updatedItems = cartItems.map((item) =>
            item.product.id === productId ? {...item, quantity: newQuantity} : item
        );
        setCartItems(updatedItems);
    }
    const handleRemoveFromCart = (productId) => {
        const updatedItems = cartItems.filter((item) => item.product.id !== productId)
        setCartItems(updatedItems);
    };
    return (
        <div className="App">
            <header className="Products"></header>
            <div>
                <Profile currentClient={currentClient} onClientChange={handleUserChange} />
            </div>

            <div><Products onAddToCart={handleAddToCart}/></div>
            <div><Cart
                cartItems={cartItems}
                onUpdateQuantity={handleUpdateQuantity}
                onRemoveFromCart={handleRemoveFromCart}
            /></div>
        </div>
    );
}

export default App;