
import { useState } from "react";
import Nav from '../../layout/Nav';
import Footer from '../../layout/Footer';
import { FaCartShopping } from "react-icons/fa6";
import { useLocation, useNavigate } from "react-router-dom";
import axios from 'axios';

const PaperShop = () => {
  const navigate = useNavigate(); 
  const location = useLocation();
  const paperPrices = {
    A3: 500,
    A4: 400,
    A5: 200,
  };

  const [selectedSize, setSelectedSize] = useState(location.state.paperType);
  const [quantity, setQuantity] = useState(1);
  const [cart, setCart] = useState([]);

  const addToCart = () => {
    const price = paperPrices[selectedSize] * quantity;
    const newCart = [...cart];
    const existingItemIndex = newCart.findIndex((item) => item.size === selectedSize);

    if (existingItemIndex >= 0) {
      newCart[existingItemIndex].quantity += quantity;
      newCart[existingItemIndex].price += price;
    } else {
      newCart.push({ size: selectedSize, quantity, price });
    }

    setCart(newCart);
  };

  const removeFromCart = (size) => {
    const updatedCart = cart.filter((item) => item.size !== size);
    setCart(updatedCart);
  };

  const handleOnBack = () => {
    navigate('/paper/choose')
  }

  const totalPrice = cart.reduce((sum, item) => sum + item.price, 0);
  const token = localStorage.getItem("accessTokenCustomer");
  const fetchPayments = async ()=>{
    const api_payment = "http://localhost:8080/payment/create";
    const body = {
      amount: totalPrice.toString()
    }
    const res = await axios.post(api_payment,body,{
      headers: {
        Authorization: `Bearer ${token}`,
      }
    });
    if(res.status == 201  && res.data.success) {
      console.log("_______________________________________________________________")
      const payUrl = res.data.data?.payUrl;
      const orderId = res.data.data?.orderId;
      if(orderId){
        console.log("Order ID:", orderId)
      }
      if(payUrl){
        console.log("Redirecting to payment URL:", payUrl)
        window.location.href = payUrl;
      }
      const fetchStatus = async ()=>{
        const api_status = "http://localhost:8080/payment/check-status";
        // let types = [];
        // let quantities = [];
        // for(let i=0; i<cart.length; i++) {
        //   types.push(cart[i].type);
        //   quantities.push(cart[i].quantity);
        // } [1, 2, 3]
        // console.log( cart )
        // const types = [A3, A4]
        // const quantities = [10, 10]
        // const types = cart && cart.length > 0 && cart.map((item) => [...types, item.size]) || [];
        // const quantities = cart && cart.length > 0 && cart.map((item) => [...types, item.quantity]) || [];
        // // console.log(types,'\n', quantities)
        const body_status = {
          orderId: orderId,
          // types: types,
          // quantities: quantities,
          items: cart.map((item) => ({
            type: item.size,
            quantity: item.quantity,
          })),
          // type: cart[0].size,
          // quantity: cart[0].quantity,
          // type: "A5",
          // quantity: "15"
        }
        const res = await axios.post(api_status,body_status,{
          // headers: {
          //   Authorization: `Bearer ${token}`,
          // }
        });
        if(res.status == 201  && res.data.success) {
          //
        } else {
          alert(res.data.message);
        }
      }
      fetchStatus();
    } else {
      alert(res.data.message);
    }

  }



  return (
    <>
     <Nav />

    <div className="paper-shop-container tw-mt-28 tw-ml-56">
      <h2 className="header">Mua giấy in</h2>

      <div className="paper-form">
        {/* Form chọn giấy */}
        <div className="box left-box">
          <div className="mb-4">
            <table>
              <tr>
                <td className="th">Khổ giấy:</td>
                <td>
                  <select
                    value={selectedSize}
                    onChange={(e) => setSelectedSize(e.target.value)}
                  >
                    {Object.keys(paperPrices).map((size) => (
                      <option className="option" key={size} value={size}>
                        {size}
                      </option>
                    ))}
                  </select>
                </td>
              </tr>

              <tr>
                <td className="th">Số lượng:</td>
                <td>
                  <input
                    type="number"
                    value={quantity}
                    onChange={(e) => setQuantity(parseInt(e.target.value || 0))}
                    min="1"
                  />
                </td>
              </tr>

              <tr>
                <td className="th">Giá:</td>
                <td>
                  <span>{paperPrices[selectedSize] * quantity} VNĐ</span>
                </td>
              </tr>
            </table>

            <button
              onClick={addToCart}
              className="button-primary"
              disabled={quantity === 0}
            >
              Thêm vào giỏ hàng
            </button>
          </div>
        </div>

        {/* Giỏ hàng */}
        <div className="box right-box">
          <div>         
          <h3 className="cart-header tw-flex"><FaCartShopping className="tw-mr-1" /> Giỏ hàng </h3>
          </div>
          {cart.length > 0 ? (
            <table className="cart-content">
              <tr>
                <td className="px-2 py-1 b">Khổ giấy</td>
                <td className="px-2 py-1 b">Số lượng</td>
                <td className="px-2 py-1 b">Giá tiền (VNĐ)</td>
                <td className="px-2 py-1 b">Hành động</td>
              </tr>
              {cart.map((item, index) => (
                <tr key={index}>
                  <td className="px-2 py-1 text-center ct">{item.size}</td>
                  <td className="px-2 py-1 text-center ct">{item.quantity}</td>
                  <td className="px-2 py-1 text-center ct">{item.price}</td>
                  <td className="px-2 py-1 text-center ct">
                    <button
                      onClick={() => removeFromCart(item.size)}
                      className="button-delete"
                    >
                      <i className="fas fa-trash-alt"></i>
                    </button>
                  </td>
                </tr>
              ))}
            </table>
          ) : (
            <p className="text-gray-500">Giỏ hàng trống.</p>
          )}
        </div>
      </div>

      {/* Tổng tiền */}
      <div className="total">
        Tổng: <span>{totalPrice.toLocaleString()} VNĐ</span>
      </div>

      {/* Nút hành động */}
      <div className="buttons">
        <button onClick={() => {fetchPayments()}}> Xác nhận </button>
        <button onClick={handleOnBack}> Hủy bỏ </button>
      </div>
    </div>

    
    <Footer />
    </>
  );
};

export default PaperShop;