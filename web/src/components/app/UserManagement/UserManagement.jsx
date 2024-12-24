import React, { useEffect, useState } from 'react';
import Header from '../../layout/Nav';
import Footer from '../../layout/Footer';
import { IoAddSharp } from "react-icons/io5";
import { CiUser } from "react-icons/ci";
import { BiLogoGmail } from "react-icons/bi";
import { apiBaseUrl } from '../../../config';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

const UserManagement = () => {
    const navigate = useNavigate();
    const [view, setView] = useState('employees');
    const [employees, setEmployees] = useState([]);
    useEffect(()=>{
      const fetchEmployee = async()=>{
        const api = `${apiBaseUrl}/users`;
        const token = localStorage.getItem("accessTokenAdmin");
        const res = await axios.get(api,{
          headers: {
            Authorization: `Bearer ${token}`,
          },
          params: {
            role: 'EMPLOYEE',
            sort: 'desc:lastName',
          },
        });
        if(res.status ===200) {
          setEmployees(res.data.data.data.map((employee)=>({
            id: employee.id.value,
            email: employee.email.value,
            name: `${employee.name.firstName} ${employee.name.lastName}`,
            phoneNumber: employee.phoneNumber,
            active: employee.isActive? 'Chưa điền thông tin' : 'Đã điền thông tin',
          })))
        }
      }
      fetchEmployee();
    },[])
    const [selectedCustomerIndex, setSelectedCustomerIndex] = useState(null);
    const [customers, setCustomers] = useState([]);
    const [isModalOpen, setIsModalOpen] = useState(false);
    const [selectedEmployeeIndex, setSelectedEmployeeIndex] = useState(null);
    const [newEmployee, setNewEmployee] = useState({
    id: '',
    name: '',
    email: '',
    active: 'Chưa điền thông tin',
    printers: [],
  });
    const handleChangeTab = async (tab) => {
      if(tab === view) {
        return;
      }
      if(tab === 'employees') {
        setView('employees')
        const api = `${apiBaseUrl}/users`;
        const token = localStorage.getItem("accessTokenAdmin");
        const res = await axios.get(api,{
          headers: {
            Authorization: `Bearer ${token}`,
          },
          params: {
            role: 'EMPLOYEE',
            sort: 'desc:lastName',
          },
        });
        if(res.status ===200) {
          setEmployees(res.data.data.data.map((employee)=>({
            id: employee.id.value,
            email: employee.email.value,
            name: `${employee.name.firstName} ${employee.name.lastName}`,
            phoneNumber: employee.phoneNumber,
            active: employee.isActive? 'Chưa điền thông tin' : 'Đã điền thông tin',
          })))
        }
      } else {
        setView("customers");
        const api = `${apiBaseUrl}/users`;
        const token = localStorage.getItem("accessTokenAdmin");
        const res = await axios.get(api,{
          headers: {
            Authorization: `Bearer ${token}`,
          },
          params: {
            role: 'CUSTOMER',
            sort: 'desc:lastName',
          },
        });
        if(res.status ===200) {
          setCustomers(res.data.data.data.map((customer)=>({
            id: customer.id.value,
            email: customer.email.value,
            name: `${customer.name.firstName} ${customer.name.lastName}`,
            phoneNumber: customer.phoneNumber,
            active: customer.isActive? 'Chưa điền thông tin' : 'Đã điền thông tin',
            paperInfo: {
              A3: '100',
              A4: '100',
              A5: '100'
            }
          })))
        }
      }
    }
  
    const [isPrinterDropdownOpen, setIsPrinterDropdownOpen] = useState(false);

    const [availablePrinters,setAvailablePrinters] = useState([]);

    const handleAddEmployee = async () => {
        const api = `${apiBaseUrl}/users/addEmployeeMail`;
        const token = localStorage.getItem("accessTokenAdmin");
        const res = await axios.post(api, {
          email: newEmployee.email,
          printers: newEmployee.printers.map((printer)=>({printerId:printer.id,isManager:true}))
        },{
          headers: {
            Authorization: `Bearer ${token}`,
          }
        });
        if(res.status === 201) {
          setEmployees([...employees, newEmployee]);
          setNewEmployee({
              id: '',
              name: '',
              email: '',
              active: 'Chưa điền thông tin',
              printers: [],
          });
          setIsModalOpen(false);
          setIsPrinterDropdownOpen(false);
      }
        
    };

    const handleOpenAddEmployeeTab = async ()=>{
        setIsModalOpen(true);
        const api = `${apiBaseUrl}/printers`;
        const token = localStorage.getItem("accessTokenAdmin");
        const res = await axios.get(api,{
          headers:{
            Authorization: `Bearer ${token}`,
          },
        });
        if(res.status===200) {
          setAvailablePrinters(res.data.data.data.map((printer)=>({
            id: printer.id.value,
            name: printer.name,
          })))
        }
    }

    const togglePrinterSelection = (printer) => {
        setNewEmployee((prev) => {
        const isSelected = prev.printers.includes(printer);
        return {
            ...prev,
            printers: isSelected
            ? prev.printers.filter((p) => p !== printer) // Bỏ chọn
            : [...prev.printers, printer], // Thêm vào danh sách
        };
        });

    };
    const toggleEmployeeDropdown = (index) => {
        setSelectedEmployeeIndex((prevIndex) => (prevIndex === index ? null : index));
      };
      const toggleCustomerDropdown = (index) => {
        setSelectedCustomerIndex((prevIndex) => (prevIndex === index ? null : index));
      };

const addPrinterToEmployee = (employeeIndex, printer) => {
    console.log(printer);
    setEmployees((prev) =>
      prev.map((employee, index) =>
        index === employeeIndex
          ? { ...employee, printers: [...employee.printers, printer] }
          : employee
      )
    );
  };

const removePrinterFromEmployee = (employeeIndex, printer) => {
    setEmployees((prev) =>
      prev.map((employee, index) =>
        index === employeeIndex
          ? { ...employee, printers: employee.printers.filter((p) => p !== printer) }
          : employee
      )
    );
  };
  console.log(newEmployee);

  return (
    <>
    <Header />
    <div> 
        {/* Header for Switching Views */}
      <div className="tabs-header">
        <button
          className={view === 'employees' ? 'active-tab' : ''}
          onClick={() => handleChangeTab("employees")}
        >
          Manage Employees
        </button>
        <button
          className={view === 'customers' ? 'active-tab' : ''}
          onClick={() => handleChangeTab("customers")}
        >
          Manage Customers
        </button>
      </div>
        {/* Main Content */}
        <div className="managment-container">
        {view === 'employees' && (
            <div>
                <header className="tw-flex tw-items-center tw-justify-between tw-mb-3">
                    <h2 className="tw-text-lg tw-leading-6 tw-font-medium tw-text-customBlue">Employee List</h2>
                    <button className="add-emp-button tw-text-customBlue tw-group tw-flex tw-items-center 
                    tw-rounded-md tw-text-sm tw-font-medium tw-px-4 tw-py-2" 
                    onClick={() => handleOpenAddEmployeeTab()}>
                      <IoAddSharp className='tw-mr-1'/>
                     New
                    </button>
                </header>
        
                <div className="employee-list-mn">
                    <table>
                    <thead>
                        <tr className='tw-text-customBlue'>
                        <th className='first'>Email</th>
                        <th>Name</th>
                        <th>Phone number</th>
                        <th className='last'>Active</th>
                        {/* <th className='last'>Actions</th> */}
                        </tr>
                    </thead>
                    <tbody>
                        {employees.map((employee, index) => (
                            <React.Fragment key={index}>
                            <tr
                            onClick={()=>{
                              navigate(`/account/employee/infor/${employee.id}`);
                              toggleEmployeeDropdown(index);
                            }}
                            className='hover:tw-bg-cyan-50'
                            >
                                <td>{employee.email}</td>
                                <td>{employee.name}</td>
                                <td>{employee.phoneNumber}</td>
                                <td>{employee.active}</td>
                            </tr>
                            {selectedEmployeeIndex === index && (
                                <tr>
                                <td colSpan="4" className="dropdown-row">
                                    <div className="printer-mana-dropdown">
                                    <h4>Printers Managed:</h4>
                                    {employee.printers.length > 0 ? (
                                        <ul>
                                        {employee.printers.map((printer, i) => (
                                            <li key={i}>
                                            {printer.name}
                                            <button
                                                className="remove-button"
                                                onClick={(e) => {
                                                e.stopPropagation();
                                                removePrinterFromEmployee(index, printer);
                                                }}
                                            >
                                                Delete
                                            </button>
                                            </li>
                                        ))}
                                        </ul>
                                    ) : (
                                        <p>No Printers Assigned</p>
                                    )}
                                    <div className="add-printer-dropdown"> 
                                        <h4>Add Printer</h4>
                                        {availablePrinters
                                        .filter((printer) => !employee.printers.some((p) => p.id === printer.id))
                                        .map((printer, i) => (
                                            <button
                                            key={i}
                                            onClick={(e) => {
                                                e.stopPropagation();
                                                addPrinterToEmployee(index, printer);
                                            }}
                                            >
                                            {printer.name}
                                            </button>
                                        ))}
                                    </div>
                                    </div>
                                </td>
                                </tr>
                            )}
                            </React.Fragment>
                        ))}
                        </tbody>
                    </table>
                </div>
            </div> 
        )}
        {view === 'customers' && (
            <div>
                <header className='tw-flex tw-items-center tw-justify-between tw-mb-3'>
                <h2 className='tw-text-lg tw-leading-6 tw-font-medium tw-text-customBlue'
                >Customer List</h2>
                <div className='none-button'></div>
                </header>
                
                {customers.length === 0 ? (
                <p>No customers available.</p>
                ) : (
                    <div className="customer-list-mn">
                        <table>
                            <thead>
                            <tr >
                                <th className='first'>Email</th>
                                <th>Name</th>
                                <th>Phone number</th>
                                <th className='last'>Active</th>
                            </tr>
                            </thead>
                            <tbody>
                            {customers.map((customer, index) => (
                                <React.Fragment key={index}>
                                <tr
                                className='hover:tw-bg-cyan-50'
                                onClick={() => {
                                  navigate(`/account/customer/infor/${customer.id}`)
                                  toggleCustomerDropdown(index)
                                }}
                                >
                                    <td>{customer.email}</td>
                                    <td>{customer.name}</td>
                                    <td>{customer.phoneNumber}</td>
                                    <td>{customer.active}</td>
                                </tr>
                                {selectedCustomerIndex === index && (
                                    <tr>
                                    <td colSpan="4" className="dropdown-row">
                                        <div className="cus-mana-dropdown">
                                        <h4>Paper Information:</h4>
                                        <ul>
                                            <li>A3: {customer.paperInfo.A3}</li>
                                            <li>A4: {customer.paperInfo.A4}</li>
                                            <li>A5: {customer.paperInfo.A5}</li>
                                        </ul>
                                        </div>
                                    </td>
                                    </tr>
                                )}
                                </React.Fragment>
                            ))}
                            </tbody>
                        </table>
                    </div>
                )}
            </div>
        )}
    </div>
    

      {isModalOpen && (
        <div className="modal-add-emp">
          <div className="modal-add-content">
          <h3>Thêm nhân viên</h3>

            <div className='tw-flex tw-font-semibold tw-text-lg'> 
            <CiUser />
            <input
              type="text"
              placeholder="Name"
              className='tw-w-[90%]'
              value={newEmployee.name}
              onChange={(e) => setNewEmployee({ ...newEmployee, name: e.target.value })}
            />
            </div>
            
            <div className='tw-flex tw-font-semibold tw-text-lg'> <BiLogoGmail />
            <input
              type="email"
              placeholder="Email"
              className='tw-w-[90%]'
              value={newEmployee.email}
              onChange={(e) => setNewEmployee({ ...newEmployee, email: e.target.value })}
            />
            </div>
            {/* <div className='select-active'>        
            <select
              value={newEmployee.active}
              onChange={(e) =>
                setNewEmployee({ ...newEmployee, active: e.target.value })
              }
            >
              <option value="Đã điền thông tin">Đã điền thông tin</option>
              <option value="Chưa điền thông tin">Chưa điền thông tin</option>
            </select>
            </div> */}

            {/* Accordion Dropdown */}
            <div className="printer-modal-dropdown">
              <div className="tw-flex">
                <i className='ti-printer tw-mr-1'></i>
                <div
                  className="dropdown-header"
                  onClick={() => setIsPrinterDropdownOpen(!isPrinterDropdownOpen)}
                >
                  <p className='tw-mb-0 tw-w-[80%]'>Printers Assigned</p>
                  <span>{isPrinterDropdownOpen ? <i className='ti-minus'></i> 
                                                : <i className='ti-plus'></i>}</span>
                </div>
              </div>
              {isPrinterDropdownOpen && (
                <div className="dropdown-body tw-ml-[25px] tw-mt-1">
                  {availablePrinters.map((printer, index) => (
                    <div key={printer.id} className="dropdown-item">
                      <input
                        type="checkbox"
                        id={`printer-${printer.id}`}
                        className='tw-mr-3'
                        checked={newEmployee.printers.some((p) => p.id === printer.id)}
                        onChange={() => togglePrinterSelection(printer)}
                      />
                      <label htmlFor={`printer-${printer.id}`}>{printer.name}</label>
                    </div>
                  ))}
                </div>
              )}
            </div>

            <div className="modal-buttons">
              <button onClick={handleAddEmployee}>Confirm</button>
              <button onClick={() => setIsModalOpen(false)}>Cancel</button>
            </div>
          </div>
        </div>
      )}
    </div>
    <Footer />
    </>
  );
};
    

export default UserManagement;
