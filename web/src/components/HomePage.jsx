
import { useState } from 'react';
import Footer from './layout/Footer';

// css template
import '../assets/css/templatemo-chain-app-dev.css';
import '../assets/css/animated.css';
import '../assets/css/owl.css';
import { googleClientId, googleRedirectURI } from '../config';


function HomePage() {
  const [show, setShow] = useState(null);

  const handleLogin = () => {
     setShow(prev => !prev);
  }
  const googleAuthUrl = `https://accounts.google.com/o/oauth2/auth/oauthchooseaccount?` +
    `response_type=code` +
    `&client_id=${googleClientId}` +
    `&redirect_uri=${encodeURIComponent(googleRedirectURI)}` +
    `&scope=${encodeURIComponent('openid profile email')}` +
    `&flowName=GeneralOAuthFlow`;
  return (
    <>
  {/* <!-- ***** Preloader End ***** -->

  {/* <!-- ***** Header Area Start ***** --> */}
      <header className="header-area header-sticky wow slideInDown" data-wow-duration="0.75s" data-wow-delay="0s">
        <div className="container">
          <div className="row">
            <div className="col-12">
              <nav className="main-nav">
                {/* <!-- ***** Logo Start ***** --> */}
                <a href="index.html" className="logo">
                  <img src="./src/assets/images/logo-new.png" alt="Chain App Dev"/>
                </a>
                {/* <!-- ***** Logo End ***** --> */}
                {/* <!-- ***** Menu Start ***** --> */}
                <ul className="nav">
                  <li className="scroll-to-section"><a href="#top">Home</a></li>
                  <li className="scroll-to-section"><a href="#services">Instruction</a></li>
                  <li className="scroll-to-section"><a href="#newsletter">Newsletter</a></li>
                  <li><div className="gradient-button"><a id="modal_trigger" onClick={handleLogin}><i className="fa fa-sign-in-alt"></i> Sign In Now</a>
                      </div></li> 
                </ul>        
                <a className='menu-trigger'>
                    <span>Menu</span>
                </a>
                {/* <!-- ***** Menu End ***** --> */}
              </nav>
            </div>
          </div>
        </div>
      </header>
  {/* <!-- ***** Header Area End ***** --> */}
      {!show ? <></> : <>
      <div id='lean_overlay' style={{display: 'block', opacity: 0.6,}}></div>
      <div id="modal" className="popupContainer" style={{display: 'block', position: 'fixed', opacity: 1, zIndex: 11000, left: '50%', marginLeft: '-165px', top: '100px',}}>
        <div className="popupHeader">
            <span className="header_title">Login</span>
            <span className="modal_close"
            onClick={handleLogin}><i className="fa fa-times"></i></span>
        </div>

        <section className="popupBody">
            {/* <!-- Social Login --> */}
            <div className="social_login">
                <div className="">
                    <a href={googleAuthUrl} className="social_box google">
                        <span className="icon"><i className="fab fa-google-plus"></i></span>
                        <span className="icon_title">Connect with Google</span>
                    </a>
                </div>
            </div>
          </section>
      </div>
      </>
      }

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

      <div id="services" className="services section">
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

      <div id="clients" className="the-clients">
        <div className="container">
          <div className="row">
            <div className="col-lg-8 offset-lg-2">
              <div className="section-heading">
                <h4>Đánh giá của <em>khách hàng</em></h4>
                <img src="./src/assets/images/heading-line-dec.png" alt=""/>
              </div>
            </div>
            <div className="col-lg-12">
              <div className="naccs">
                <div className="grid">
                  <div className="row">
                    <div className="col-lg-7 align-self-center">
                      <div className="menu">
                        <div className="first-thumb active">
                          <div className="thumb">
                            <div className="row">
                              <div className="col-lg-4 col-sm-4 col-12">
                                <h4>Nguyễn Bảo Trân</h4>
                                <span className="date">30 November 2023</span>
                              </div>
                              <div className="col-lg-4 col-sm-4 d-none d-sm-block">
                                <span className="category">Khoa Cơ khí</span>
                              </div>
                              <div className="col-lg-4 col-sm-4 col-12">
                                  <i className="fa fa-star"></i>
                                  <i className="fa fa-star"></i>
                                  <i className="fa fa-star"></i>
                                  <i className="fa fa-star"></i>
                                  <i className="fa fa-star"></i>
                                  <span className="rating">4.8</span>
                              </div>
                            </div>
                          </div>
                        </div>
                        <div>
                          <div className="thumb">
                            <div className="row">
                              <div className="col-lg-4 col-sm-4 col-12">
                                <h4>Trần Nguyên Bảo</h4>
                                <span className="date">29 November 2023</span>
                              </div>
                              <div className="col-lg-4 col-sm-4 d-none d-sm-block">
                                <span className="category">Khoa Điện - Điện tử</span>
                              </div>
                              <div className="col-lg-4 col-sm-4 col-12">
                                  <i className="fa fa-star"></i>
                                  <i className="fa fa-star"></i>
                                  <i className="fa fa-star"></i>
                                  <i className="fa fa-star"></i>
                                  <i className="fa fa-star"></i>
                                  <span className="rating">4.5</span>
                              </div>
                            </div>
                          </div>
                        </div>
                        <div>
                          <div className="thumb">
                            <div className="row">
                              <div className="col-lg-4 col-sm-4 col-12">
                                <h4>Lê Văn Thoại</h4>
                                <span className="date">27 November 2023</span>
                              </div>
                              <div className="col-lg-4 col-sm-4 d-none d-sm-block">
                                <span className="category">Khoa KH&amp;KT Máy tính</span>
                              </div>
                              <div className="col-lg-4 col-sm-4 col-12">
                                  <i className="fa fa-star"></i>
                                  <i className="fa fa-star"></i>
                                  <i className="fa fa-star"></i>
                                  <i className="fa fa-star"></i>
                                  <i className="fa fa-star"></i>
                                  <span className="rating">4.7</span>
                              </div>
                            </div>
                          </div>
                        </div>
                        <div>
                          <div className="thumb">
                            <div className="row">
                              <div className="col-lg-4 col-sm-4 col-12">
                                <h4>Trang Hồng Ngọc</h4>
                                <span className="date">24 November 2023</span>
                              </div>
                              <div className="col-lg-4 col-sm-4 d-none d-sm-block">
                                <span className="category">Khoa Kỹ thuật Xây dựng</span>
                              </div>
                              <div className="col-lg-4 col-sm-4 col-12">
                                  <i className="fa fa-star"></i>
                                  <i className="fa fa-star"></i>
                                  <i className="fa fa-star"></i>
                                  <i className="fa fa-star"></i>
                                  <i className="fa fa-star"></i>
                                  <span className="rating">3.9</span>
                              </div>
                            </div>
                          </div>
                        </div>
                        <div className="last-thumb">
                          <div className="thumb">
                            <div className="row">
                              <div className="col-lg-4 col-sm-4 col-12">
                                <h4>Đỗ Văn Bâng</h4>
                                <span className="date">21 November 2023</span>
                              </div>
                              <div className="col-lg-4 col-sm-4 d-none d-sm-block">
                                <span className="category">Khoa Kỹ thuật Hóa học</span>
                              </div>
                              <div className="col-lg-4 col-sm-4 col-12">
                                  <i className="fa fa-star"></i>
                                  <i className="fa fa-star"></i>
                                  <i className="fa fa-star"></i>
                                  <i className="fa fa-star"></i>
                                  <i className="fa fa-star"></i>
                                  <span className="rating">4.3</span>
                              </div>
                            </div>
                          </div>
                        </div>
                      </div>
                    </div> 
                    <div className="col-lg-5">
                      <ul className="nacc">
                        <li className="active">
                          <div>
                            <div className="thumb">
                              <div className="row">
                                <div className="col-lg-12">
                                  <div className="client-content">
                                    <img src="./src/assets/images/quote.png" alt=""/>
                                    <p>“Chất lượng in tốt, in rõ nét, giấy xịn, nhân viên siêu nhiệt tình. Nói chung là 9.5 điểm ạ!”</p>
                                  </div>
                                  <div className="down-content">
                                    <img src="./src/assets/images/client-image.jpg" alt=""/>
                                    <div className="right-content">
                                      <h4>Nguyễn Bảo Trân</h4>
                                      <span>Khoa Cơ khí</span>
                                    </div>
                                  </div>
                                </div>
                              </div>
                            </div>
                          </div>
                        </li>
                        <li>
                          <div>
                            <div className="thumb">
                              <div className="row">
                                <div className="col-lg-12">
                                  <div className="client-content">
                                    <img src="./src/assets/images/quote.png" alt=""/>
                                    <p>“CTO, Lorem ipsum dolor sit amet, consectetur adpiscing elit, sed do eismod tempor idunte ut labore et dolore magna aliqua darwin kengan
                                      lorem ipsum dolor sit amet, consectetur picing elit massive big blasta.”</p>
                                  </div>
                                  <div className="down-content">
                                    <img src="./src/assets/images/client-image.jpg" alt=""/>
                                    <div className="right-content">
                                      <h4>Jake H. Nyo</h4>
                                      <span>CTO of Digital Company</span>
                                    </div>
                                  </div>
                                </div>
                              </div>
                            </div>
                          </div>
                        </li>
                        <li>
                          <div>
                            <div className="thumb">
                              <div className="row">
                                <div className="col-lg-12">
                                  <div className="client-content">
                                    <img src="./src/assets/images/quote.png" alt=""/>
                                    <p>“May, Lorem ipsum dolor sit amet, consectetur adpiscing elit, sed do eismod tempor idunte ut labore et dolore magna aliqua darwin kengan
                                      lorem ipsum dolor sit amet, consectetur picing elit massive big blasta.”</p>
                                  </div>
                                  <div className="down-content">
                                    <img src="./src/assets/images/client-image.jpg" alt=""/>
                                    <div className="right-content">
                                      <h4>May C.</h4>
                                      <span>Founder of Catherina Co.</span>
                                    </div>
                                  </div>
                                </div>
                              </div>
                            </div>
                          </div>
                        </li>
                        <li>
                          <div>
                            <div className="thumb">
                              <div className="row">
                                <div className="col-lg-12">
                                  <div className="client-content">
                                    <img src="./src/assets/images/quote.png" alt=""/>
                                    <p>“Lorem ipsum dolor sit amet, consectetur adpiscing elit, sed do eismod tempor idunte ut labore et dolore magna aliqua darwin kengan
                                      lorem ipsum dolor sit amet, consectetur picing elit massive big blasta.”</p>
                                  </div>
                                  <div className="down-content">
                                    <img src="./src/assets/images/client-image.jpg" alt=""/>
                                    <div className="right-content">
                                      <h4>Random Staff</h4>
                                      <span>Manager, Digital Company</span>
                                    </div>
                                  </div>
                                </div>
                              </div>
                            </div>
                          </div>
                        </li>
                        <li>
                          <div>
                            <div className="thumb">
                              <div className="row">
                                <div className="col-lg-12">
                                  <div className="client-content">
                                    <img src="./src/assets/images/quote.png" alt=""/>
                                    <p>“Mark, Lorem ipsum dolor sit amet, consectetur adpiscing elit, sed do eismod tempor idunte ut labore et dolore magna aliqua darwin kengan
                                      lorem ipsum dolor sit amet, consectetur picing elit massive big blasta.”</p>
                                  </div>
                                  <div className="down-content">
                                    <img src="./src/assets/images/client-image.jpg" alt=""/>
                                    <div className="right-content">
                                      <h4>Mark Am</h4>
                                      <span>CTO, Amber Do Company</span>
                                    </div>
                                  </div>
                                </div>
                              </div>
                            </div>
                          </div>
                        </li>
                      </ul>
                    </div>          
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>

      <Footer />
    </>
  )
}

export default HomePage;
