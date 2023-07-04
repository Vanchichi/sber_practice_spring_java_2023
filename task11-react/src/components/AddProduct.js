import React, { useState } from 'react';

export const AddProductForm = ({ onAddProduct }) => {
    const [name, setName] = useState('');
    const [imageUrl, setImageUrl] =useState('');
    const [quantity, setQuantity] = useState('');
    const [price, setPrice] = useState('');

    const handleNameChange = (e) => {
        setName(e.target.value);
    };

    const handleImageUrlChange = (e) => {
        setImageUrl(e.target.value);
    };

    const handleQuantityChange = (e) => {
        setQuantity(e.target.value);
    };

    const handlePriceChange = (e) => {
        setPrice(e.target.value);
    };

    const handleSubmit = (e) => {
        e.preventDefault();

        const newProduct = {

            name: name,
            imageUrl: imageUrl,
            quantity: parseInt(quantity),
            price: parseFloat(price),
        };

        onAddProduct(newProduct);


        setName('');
        setQuantity('');
        setPrice('');
    };

    return (
        <form onSubmit={handleSubmit}>
            <input
                type="text"
                placeholder="Название продукта"
                value={name}
                onChange={handleNameChange}
                required
            />
            <input
                type="text"
                placeholder="Ссылка на изображение"
                value={imageUrl}
                onChange={handleImageUrlChange}
                required
            />
            <input
                type="number"
                placeholder="Количество"
                value={quantity}
                onChange={handleQuantityChange}
                required
            />

            <input
                type="number"
                placeholder="Стоимость"
                value={price}
                onChange={handlePriceChange}
                required
            />
            <button type="submit" className={"button"}>Добавить продукт</button>
        </form>
    );
};