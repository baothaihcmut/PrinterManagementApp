// css template
import { VscFiles } from "react-icons/vsc";
import { TfiPrinter } from "react-icons/tfi";
import { Link } from "react-router-dom";
import Nav from '../../layout/Nav';


function AfterLogin() {
  const token = localStorage.getItem("accessTokenEmployee");
  console.log(token);
  return (
    <>
      <Nav />

      <div className="main-banner wow fadeIn" id="top" data-wow-duration="1s" data-wow-delay="0.5s">
        <div className="container">
          <div className="row">
            <div className="col-lg-12">
              <div className="row">
                <div className="col-lg-6 align-self-center">
                  <div className="left-content show-up header-text wow fadeInLeft" data-wow-duration="1s" data-wow-delay="1s">
                    <div className="row">
                      <div className="col-lg-12">
                        <h2>HCMUT SPSS</h2>
                        <p>Dịch vụ in ấn thông minh dành cho sinh viên trường Đại học Bách khoa - ĐHQG-HCM</p>
                      </div>
                      <div className="col-lg-12">
                        <div className="white-button first-button scroll-to-section">
                          <Link to="/paper/choose">Mua giấy in <VscFiles className='tw-ml-1'/> </Link>
                        </div>
                        <div className="white-button scroll-to-section">
                        <Link to="/upload">In tài liệu <TfiPrinter className='tw-ml-1'/> </Link>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
                <div className="col-lg-6">
                  <div className="right-image wow fadeInRight" data-wow-duration="1s" data-wow-delay="0.5s">
                    <img src="./src/assets/images/slider-dec-new.png" alt=""/>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>

      <div id="services-after-login" className="services section">
        <div className="container">
          <div className="row">
            <div className="col-lg-8 offset-lg-2">
              <div className="section-heading  wow fadeInDown" data-wow-duration="1s" data-wow-delay="0.5s">
                <h4><em>Hướng dẫn sử dụng</em> dịch vụ</h4>
                <img src="./src/assets/images/heading-line-dec.png" alt=""/>
              </div>
            </div>
          </div>
        </div>
        <div className="container">
          <div className="row">
            <div className="col-lg-3">
              <div className="service-item first-service">
                <div className="icon"></div>
                <h4>Bước 1</h4>
                <p>Đăng ký/Đăng nhập vào trang web để sử dụng dịch vụ</p>
              </div>
            </div>
            <div className="col-lg-3">
              <div className="service-item second-service">
                <div className="icon"></div>
                <h4>Bước 2</h4>
                <p>Tiến hành mua loại giấy in phù hợp với mong muốn</p>
              </div>
            </div>
            <div className="col-lg-3">
              <div className="service-item third-service">
                <div className="icon"></div>
                <h4>Bước 3</h4>
                <p>Gửi các thông tin về file in tiến hành xác nhận giao dịch</p>
              </div>
            </div>
            <div className="col-lg-3">
              <div className="service-item fourth-service">
                <div className="icon"></div>
                <h4>Bước 4</h4>
                <p>Theo dõi tình trạng đơn in và nhận hàng. Đánh giá dịch vụ</p>
              </div>
            </div>
          </div>
        </div>
      </div>
    </>
  )
}

export default AfterLogin;