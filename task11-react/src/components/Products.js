import React, {useState} from 'react';
import {AddToCartButton} from './AddToCartButton';
import {SearchBar} from './SearchBar';
import {AddProductForm} from './AddProduct';
import {Cart} from './Cart';

export const Products = () => {
    const [products, setProducts] = useState([
        {
            id: 1,
            name: 'Яблоко',
            price: 60.0,
            imageUrl:
                'https://raw.githubusercontent.com/ZuevKirill95/spring-practice-source-code/main/html-js-css/img/apple.png',
            quantity: 18,
        },
        {
            id: 2,
            name: 'Персик',
            price: 150.0,
            imageUrl:
                'https://raw.githubusercontent.com/ZuevKirill95/spring-practice-source-code/main/html-js-css/img/peach.png',
            quantity: 2,
        },
        {
            id: 3,
            name: 'Апельсин',
            price: 80.0,
            imageUrl:
                'https://raw.githubusercontent.com/ZuevKirill95/spring-practice-source-code/main/html-js-css/img/orange.png',
            quantity: 7,
        },
        {
            id: 4,
            name: 'Банан',
            price: 90.0,
            imageUrl:
                'https://raw.githubusercontent.com/ZuevKirill95/spring-practice-source-code/main/html-js-css/img/banana.png',
            quantity: 9,
        },
        {
            id: 5,
            name: 'Арбуз',
            price: 180.0,
            imageUrl:
                'https://raw.githubusercontent.com/ZuevKirill95/spring-practice-source-code/main/html-js-css/img/watermelon.png',
            quantity: 12,
        },
    ]);

    const [filteredProducts, setFilteredProducts] = useState(products);
    const [cart, setCart] = useState([]);

    const handleAddProduct = (newProduct) => {
        const newId = products.length + 1;
        const updatedProducts = [...products, {...newProduct, id: newId}];
        setProducts(updatedProducts);
        setFilteredProducts(updatedProducts);
    };

    const onAddToCart = (product) => {
        const existingCartItem = cart.find((item) => item.product.id === product.id);

        if (existingCartItem) {
            const updatedCart = cart.map((item) =>
                item.product.id === product.id
                    ? {...item, quantity: item.quantity + 1}
                    : item
            );
            setCart(updatedCart);
        } else {
            const updatedCart = [...cart, {product, quantity: 1}];
            setCart(updatedCart);
        }
    };

    const onRemoveFromCart = (product) => {
        const updatedCart = cart.filter((item) => item.product.id !== product.id);
        setCart(updatedCart);
    };

    const handleProductSearch = (searchTerm) => {
        const filtered = products.filter((product) =>
            product.name.toLowerCase().includes(searchTerm.toLowerCase())
        );
        setFilteredProducts(filtered);
    };

    const onUpdateQuantity = (product, newQuantity) => {
        const updatedCart = cart.map((item) =>
            item.product.id === product.id ? {...item, quantity: newQuantity} : item
        );
        setCart(updatedCart);
    };

    const onPayment = () => {
        alert('Оплата прошла успешно');

        setCart([]);
    };

    return (
        <div>
            <h2>Ассортимент</h2>

            <h3>Поиск товаров по имени</h3>
            <div className={'search'}>
            <SearchBar onSearch={handleProductSearch}/>
            </div>
            <h3>Добавление товара</h3>
            <div className={'add'}>
            <AddProductForm onAddProduct={handleAddProduct}/>
            </div>
            <ul>
                {filteredProducts.map((product) => (
                    <div key={product.id} className={'products-item'}>
                        <div>{product.name}</div>
                        <div>
                            <img
                                className="avatar"
                                src={product.imageUrl}
                                alt={'Фото ' + product.name}
                                style={{
                                    width: 80,
                                    height: 80,
                                }}
                            />
                        </div>
                        <div>Цена: {product.price}</div>
                        <div>Количество: {product.quantity}</div>
                        <AddToCartButton product={product} onAddToCart={onAddToCart}/>
                    </div>
                ))}
            </ul>
            <h2>Корзина клиента</h2>
            <Cart
                cartItems={cart}
                onRemoveFromCart={onRemoveFromCart}
                onUpdateQuantity={onUpdateQuantity}
                onPayment={onPayment}
            />
            <button className={"buttonpay"} onClick={onPayment}>Оплата</button>
        </div>
    );
};