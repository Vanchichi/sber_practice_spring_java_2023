export const AddToCartButton = ({ product, onAddToCart }) => {
    const handleAddToCart = () => {
        onAddToCart(product, product.quantity);
    };

    return (
        <button className={"button"} onClick={handleAddToCart}>Добавить в корзину</button>
    );
};