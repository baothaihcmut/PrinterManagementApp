import React, { useState } from 'react';
import Nav from '../../layout/Nav';
import Footer from '../../layout/Footer';
import { useNavigate } from 'react-router-dom';

const ChoosePaper = () => {
    const navigate = useNavigate();
    const ChoosePaper = [
      { name: "A3", width: "42.00 cm", height: "29.70 cm", price: "500 VNĐ" },
      { name: "A4", width: "29.70 cm", height: "21.00 cm", price: "400 VNĐ" },
      { name: "A5", width: "21.00 cm", height: "14.80 cm", price: "200 VNĐ" },
    ];

    const [activeCard, setActiveCard] = useState(null); // Lưu trữ ID của card được chọn

    const handleCardClick = (index) => {
      setActiveCard(index); // Cập nhật ID của card được click
    };

    const handleOnClick = () => {
      navigate('/paper/shop')
    }
    const handleOnBack = () => {
      navigate('/afterlogin')
    }

    return (
      <>
    <Nav />
    <div className="tw-ml-[240px] tw-mt-24   choose-paper-container">
      <h2 className="choose-paper-header">Mua giấy in</h2>
      <p className="choose-paper-header-title">Vui lòng chọn loại giấy muốn mua!</p>
      <div className="choose-paper-content">
        {ChoosePaper.map((paper, index) => (
          <div key={index} 
          // className="card"
          className={`card ${activeCard === index ? "active" : ""}`}
          onClick={() => handleCardClick(index)}
          >
            <div className="card-body">
              <h3>Giấy {paper.name}</h3>
              <p>Kích thước: {paper.width} x {paper.height}</p>
              <p>Giá: {paper.price}</p>
            </div>
          </div>
        ))}
      </div>
      {/* Các nút hành động */}
      <div className="buttons">
          <button onClick={handleOnClick}>Tiếp tục</button>
          <button onClick={handleOnBack}>Quay lại</button>
        </div>
    </div>
    <Footer />
    </>
  );
  };
  
  export default ChoosePaper;
  