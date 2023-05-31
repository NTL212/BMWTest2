<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<c:url value = "/view/client/assets" var="url"/>

  <!-- Start header section -->
<%--   <jsp:include page = "./header/mainHeader.jsp" flush = "true" />
 --%>  <!-- / header section -->
<!--  content -->
 <!-- catg header banner section -->
  <section id="aa-catg-head-banner">
  <img src="${pageContext.request.contextPath}/view/client/assets/images/archive-banner.png" alt="banner blog">
    <div class="aa-catg-head-banner-area">
     <div class="container">
      <div class="aa-catg-head-banner-content">
        <h2>Đăng ký</h2>
        <ol class="breadcrumb">
          <li><a href="${pageContext.request.contextPath}/">Trang chủ</a></li>                   
          <li style="color:#fff">Đăng ký tài khoản</li>
        </ol>
      </div>
     </div>
   </div>
  </section>
  <!-- / catg header banner section -->

 <!-- Cart view section -->
 <section id="aa-myaccount">
   <div class="container">
     <div class="row">
       <div class="col-md-12">
        <div class="aa-myaccount-area">         
            <div class="row">
       
              <div class="col-md-8 col-md-push-2">
                <div class="aa-myaccount-register">                 
                 <h4>Đăng ký</h4>
                 <form name="formRegister" class="aa-login-form" method="post" action="${pageContext.request.contextPath}/view/client/register" >
                    <label for="">Họ tên<span>*</span></label>
                    <input type="text" placeholder="Name" name="name" autocomplete="off" required>   
                     <label for="">Email <span>*</span></label>
                    <input type="text" placeholder="useremail@gmail.com" autocomplete="off" name="email" required>
                    <label for="">Số điện thoại<span>*</span></label>
                    <input type="text" placeholder="Số điện thoại" autocomplete="off" name="phone" required>   
                     <label for="">Username<span>*</span></label>
                    <input type="text" placeholder="Username" autocomplete="off" name="username" required>
                    <label for="">Mật khẩu<span>*</span></label>  
                    <input type="password" placeholder="Password" autocomplete="off" name="password" required>     
                    <input type="date" id="the-date" style="display: none;" name="created">             
                      
                    <p style="color:red; display: block;"><%=(request.getAttribute("errMessage") == null) ? ""
        			: request.getAttribute("errMessage")%></p>
			        <p style="color:red; display: block;"><%=(request.getAttribute("Message") == null) ? ""
			        : request.getAttribute("Message")%></p>      
			         <button type="reset" class="aa-browse-btn" value="Reset">Đặt lại</button>  
                      <button type="submit" class="aa-browse-btn">Đăng ký</button>                         
                  </form>
                </div>
              </div>
            </div>          
         </div>
       </div>
     </div>
   </div>
 </section>
 <!-- / Cart view section -->
   <script>
// Lấy ngày tháng năm hiện tại
   var today = new Date();

   // Lấy thông tin ngày, tháng, năm
   var day = today.getDate();
   var month = today.getMonth() + 1; // Tháng bắt đầu từ 0, nên cần +1
   var year = today.getFullYear();

   // Định dạng ngày và tháng thành chuỗi 2 ký tự (vd: 01, 02, ..., 10, 11, ...)
   day = day < 10 ? '0' + day : day;
   month = month < 10 ? '0' + month : month;

   // Định dạng ngày/tháng/năm
	var formattedDate = year + "-" + month + "-" + day;
	
   document.getElementById('the-date').value = formattedDate;
</script>
<!--  end content-->

<!--  footer-->
<%--  <jsp:include page = "./footer/footer.jsp" flush = "true" />
 --%><!-- end footer-->
  
  
