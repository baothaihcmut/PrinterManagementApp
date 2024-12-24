

export default function Footer() {
  return (
    <footer id="newsletter">
        <div className="container">
          <div className="row">
            <div className="col-lg-8 offset-lg-2">
              <div className="section-heading">
                <h4>Tham gia vào danh sách gửi thư để cập nhật các thông tin mới nhất cùng các khuyến mãi đặc biệt </h4>
              </div>
            </div>
            <div className="col-lg-6 offset-lg-3">
              <form id="search" action="#" method="GET">
                <div className="row">
                  <div className="col-lg-6 col-sm-6">
                    <fieldset>
                      <input type="address" name="address" className="email" placeholder="Email Address..." autoComplete="on" required/>
                    </fieldset>
                  </div>
                  <div className="col-lg-6 col-sm-6">
                    <fieldset>
                      <button type="submit" className="main-button">Subscribe Now <i className="fa fa-angle-right"></i></button>
                    </fieldset>
                  </div>
                </div>
              </form>
            </div>
          </div>
          <div className="row">
            <div className="col-lg-3">
              <div className="footer-widget">
                <h4>Cơ sở Lý Thường Kiệt</h4>
                <p></p>
                <p><a href="#">SĐT: 091-021-5340</a></p>
                <p><a href="#">Địa chỉ: Phòng 104, tòa A5, trường ĐH Bách khoa</a></p>
              </div>
            </div>
            <div className="col-lg-3">
              <div className="footer-widget">
                <h4>Cơ sở Dĩ An</h4>
                <p></p>
                <p><a href="#">SĐT: 098-620-0347</a></p>
                <p><a href="#">Địa chỉ: Phòng 106, tòa BK.B6, trường ĐH Bách khoa </a></p>
              </div>
            </div>
            <div className="col-lg-3">
              <div className="footer-widget">
                <h4>Về chúng tôi</h4>
                <ul>
                  <li><a href="#">Trang chủ</a></li>
                  <li><a href="#">Hướng dẫn</a></li>
                </ul>
                <ul>
                  <li><a href="#">Đánh giá</a></li>
                </ul>
              </div>
            </div>
          
            <div className="col-lg-3">
              <div className="footer-widget">
                <h4>Về dịch vụ của chúng tôi</h4>
                <div className="logo">
                  <img src="./src/assets/images/logo-new.png" alt=""/>
                </div>
                <p>Sự hài lòng của khách hàng là niềm vinh dự của chúng tôi.</p>
                <p><a href="#">Email: hcmut.spss@hcmut.edu.vn</a></p>
              </div>
            </div>
            <div className="col-lg-12">
              <div className="copyright-text">
                <p>Copyright © 2023 HCMUT SPSS Company. All Rights Reserved.</p>
              </div>
            </div>
          </div>
        </div>
      </footer>
  )
}