export const Cart = ({cartItems, onRemoveFromCart, onUpdateQuantity, onPayment}) => {
    const handleRemoveFromCart = (product) => {
        onRemoveFromCart(product);
    };

    const handleUpdateQuantity = (product, newQuantity) => {
        onUpdateQuantity(product, newQuantity);
    };


    const handlePayment = () => {
        onPayment();
    };

    return (
        <div>
            <div>
                <ul>
                    {cartItems.map((item) => (
                        <li key={item.product.id} className={'products-item'}>
                            <div>{item.product.name} </div>
                            <div>
                                <img
                                    className="avatar"
                                    src={item.product.imageUrl}
                                    alt={'Фото ' + item.product.name}
                                    style={{
                                        width: 80,
                                        height: 80,
                                    }}
                                />
                            </div>
                            <div>Цена: {item.product.price}</div>
                            <div>
                                Количество: {item.quantity}{' '}
                                <button
                                    onClick={() => handleUpdateQuantity(item.product, item.quantity + 1)}
                                >
                                    +
                                </button>
                                <button
                                    onClick={() => handleUpdateQuantity(item.product, item.quantity - 1)}
                                    disabled={item.quantity <= 1}
                                >
                                    -
                                </button>
                            </div>
                            <button className={"button"} onClick={() => handleRemoveFromCart(item.product)}>
                                Удалить из корзины
                            </button>
                        </li>
                    ))}
                </ul>
            </div>
        </div>
    );
};