
import {
  // BrowserRouter, Routes, Route
  createBrowserRouter, RouterProvider,
} from "react-router-dom";
import HomePage from './components/HomePage';
import ErrorPage from "./components/errors/ErrorPage";
import LoginPage from "./components/login/LoginPage";
import Home from "./components/app/Home";
import UploadFile from "./components/app/upload/UploadFile";
import Printer from "./components/app/upload/Printer";
import Printsetting from "./components/app/Printsetting/PrintSettings"
import ConfirmPrint from "./components/app/ConfirmPrint/ConfirmPrint.jsx"
import TransactionManagement from "./components/app/Transaction/TransactionManagement.jsx";
import AfterLogin from "./components/app/AfterLogin/AfterLogin.jsx";
import ChoosePaper from "./components/app/BuyPaper/ChoosePaper.jsx";
import PaperShop from "./components/app/BuyPaper/PaperShop.jsx";

import CustomerManagement from './components/app/Transaction/CustomerManagement.jsx'
import PrintManagement from "./components/app/DetailTransaction/PrintManagementDetail.jsx";
import PrintManagementCus from "./components/app/DetailTransaction/PrintManagementCus.jsx";
import AdminManagementDetail from "./components/app/DetailTransaction/AdminManagementDetail.jsx";
import UserManagement from "./components/app/UserManagement/UserManagement.jsx";
import AdminManagement from "./components/app/Transaction/AdminMangement.jsx"
import PrinterManagement from "./components/app/Transaction/PrinterManagement.jsx";
import CusAccount from "./components/app/InforAccount/CusAccount.jsx";

// import PrinterList from "./components/app/transaction/PrinterList.jsx";
// import PrinterTransaction from "./components/app/transaction/PrinterTransaction.jsx";
// import PrintManagement from "./components/app/PrintManagement/PrintManagement.jsx";
// import PrintManagementCus from "./components/app/PrintManagement/PrintManagement-Cus.jsx";
// import PrintManagementAdmin from "./components/app/PrintManagement/PrintManagementAdmin.jsx";

// import ChoosePrinter from "./components/app/Printer/Printerchoose.jsx";
import InforPrinter from "./components/app/Printer/InforPrinter.jsx";
import EmployeeInfo from "./components/app/InforAccount/EmployAccount.jsx";
import ChoosePrinter from "./components/app/Printer/ChoosePrinter.jsx";
import AdminChoosePrinter from "./components/app/Printer/AdminPrinterchoose.jsx";
const router = createBrowserRouter([
  {
    path: '/',
    element: <HomePage />,
    errorElement: <ErrorPage />, //done
  },
  {
    path: '/home',
    element:  <Home />,
    errorElement: <ErrorPage />, //done
  },
  {
    path: '/upload',
    element: <UploadFile />,
    errorElement: <ErrorPage />, //done
  },
  {
    path: '/login',
    element: <LoginPage />,
    errorElement: <ErrorPage />, //done
  },
  {
    path: '/upload/printer',
    element: <Printer />,
    errorElement: <ErrorPage />, //done
  },
  {
    path: '/upload/printsetting',
    element: <Printsetting />,
    errorElement: <ErrorPage />, //done
  },
  {
    path: '/upload/confirm',
    element: <ConfirmPrint />,
    errorElement: <ErrorPage />, //done
  },
  {
    path: '/employee/management/transaction/:id',
    element: <TransactionManagement />,
    errorElement: <ErrorPage />, 
  },
  {
    path: '/employee/printers',
    element: <ChoosePrinter />,
    errorElement: <ErrorPage />, 
  },
  {
    path: '/customer/management/transaction',
    element: <CustomerManagement />,
    errorElement: <ErrorPage />,
  },
  {
    path: '/admin/management/transaction',
    element: <AdminManagement />,
    errorElement: <ErrorPage />,
  },
  {
    path: '/afterlogin',
    element: <AfterLogin />,
    errorElement: <ErrorPage />, //done
  },
  {
    path: '/paper/choose',
    element: <ChoosePaper />,
    errorElement: <ErrorPage />, //done
  },
  {
    path: '/paper/shop',
    element: <PaperShop />,
    errorElement: <ErrorPage />, //done
  },
  {
    path: '/employee/print/detail/:id',
    element: <PrintManagement />,
    errorElement: <ErrorPage />, //done
  },
  {
    path: '/customer/print/detail/:id',
    element: <PrintManagementCus />,
    
    errorElement: <ErrorPage />, //done
  },
  {
    path: '/employee/management/transaction',
    element: <TransactionManagement />,
    errorElement: <ErrorPage />,
  },
//   {
//   path: '/managementad',
//   element: <PrintManagementAdmin />,
//   errorElement: <ErrorPage />,
// },
  {
    path: '/afterlogin',
    element: <AfterLogin />,
    errorElement: <ErrorPage />, //done
  },
  {
    path: '/printer/infor/:id',
    element: <InforPrinter/>,
    errorElement: <ErrorPage />,  //done
  },
  {
    path: '/admin/print/detail/:id',
    element: <AdminManagementDetail />,
    errorElement: <ErrorPage />, //done
  },
  {
    path: '/admin/management/user',
    element: <UserManagement />,
    errorElement: <ErrorPage />, 
  },
  {
    path: '/admin/management/printer',
    element: <PrinterManagement />,
    errorElement: <ErrorPage />,
  },
  {
    path: '/account/customer/infor/:id',
    element: <CusAccount />,
    errorElement: <ErrorPage />,
  },
  {
    path: '/account/employee/infor/:id',
    element: <EmployeeInfo/>,
    errorElement: <ErrorPage />, 
  },
  {
    path: '/admin/printers',
    element: <AdminChoosePrinter/>,
    errorElement:<ErrorPage />,
  }
]);

function App() {
  
  return (
    // <BrowserRouter future={{ v7_startTransition: true }}>
    //   <Routes>
    //     <Route path="/" element={<HomePage />}/>
    //   </Routes>
    // </BrowserRouter>
    
    <RouterProvider 
      router={router} 
      future={{
        v7_startTransition: true,
      }
    }/>

  );
}

export default App;
