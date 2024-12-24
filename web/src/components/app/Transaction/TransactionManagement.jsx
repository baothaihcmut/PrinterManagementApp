
import { useState, useCallback, useEffect, lazy, Suspense} from "react";
import Nav from '../../layout/Nav.jsx';
import { apiBaseUrl } from "../../../config.js";
import axios from "axios";
import { useParams } from "react-router-dom";
const LazyLoading = lazy(() => import('./LazyLoading.jsx'));







const TransactionManagement = () => {
  
  const {id} = useParams();
  const [status, setStatus] = useState("pending");
  const [visibleData, setVisibleData] = useState([]);
  const [page, setPage] = useState(1); // Current page
  const [hasMore, setHasMore] = useState(true); // Whether more data is available

  const fetchAdminTransactions = useCallback(async () => {
    try {
      const token = localStorage.getItem("accessTokenEmployee");
      let res;
      if(status === 'pending') {
        const api_transactions = `${apiBaseUrl}/printers/${id}/transactions`;
        res = await axios.get(api_transactions, {
          headers: { Authorization: `Bearer ${token}` },
          params: {
            status: 'PENDING', 
            page,
            sort: "desc:createdAt" ,
          }, // Include page in params
        });
      }else{
        const api_transactions = `${apiBaseUrl}/employees/transactions`;
        res = await axios.get(api_transactions, {
          headers: {
            Authorization: `Bearer ${token}`,
          },
          params: {
            status: status.toUpperCase(),
            printer: id,
            sort: "desc:acceptedAt",
          }
        });
      }

      if (res.status === 200) {
        const fetchedTransactions = res.data.data.data.map((transaction) => ({
          id: transaction.id.value,
          transactionId: transaction.transactionId,
          name: transaction.name,
          customerName: `${transaction.customerName.firstName} ${transaction.customerName.lastName}`,
          createdAt: new Date(transaction.createdAt).toLocaleString(),
        }));
        setVisibleData((prev) => [...prev, ...fetchedTransactions]); // Append new data
        setHasMore(fetchedTransactions.length > 0); // Check if there's more data
      } else {
        console.error("Failed to fetch transactions:", res.data.message);
        setHasMore(false);
      }
    } catch (error) {
      console.error("Error fetching transactions:", error);
      setHasMore(false);
    }
  }, [status, page]);

  const handleScroll = (e) => {
    const { scrollTop, clientHeight, scrollHeight } = e.target;
    if (scrollTop + clientHeight >= scrollHeight - 50 && hasMore) {
      setPage((prevPage) => prevPage + 1); // Increment page
    }
  };

  const handleChange = async (item) => {
    const api = `${apiBaseUrl}/transactions/${item.id}/${status==='pending'?'accept':'done'}`;
    const token = localStorage.getItem("accessTokenEmployee");
    const res = await axios.patch(api,{},{
      headers:{
        Authorization: `Bearer ${token}`,
      }
    });
    if(res.status === 201) {
      // handle
      setVisibleData((prev)=>prev.map((trans)=>trans.id===item.id?{...trans,isChange:true}:trans));
    }
  }
  useEffect(() => {
    setTimeout(()=>{
      fetchAdminTransactions();
    },1000)
    
  }, [page, status]);

  useEffect(() => {
    setVisibleData([]);
    setPage(1);
    setHasMore(true);
  }, [status]);
  const hoverButton = (() => "tw-text-gray-500 tw-bg-white tw-transition-all hover:tw-translate-y-[-4px]")();

  console.log(visibleData);

  return (
    <>
      <Nav />
      <div className="tw-mt-28 tw-ml-52">

        {/* status gd */}
        <div className="tw-mt-36">
          <h2 className="tw-text-center tw-text-customBlue tw-font-semibold tw-mb-6">Transaction Management</h2>
          <div className="tw-flex tw-mx-auto tw-w-[90%] tw-h-20 tw-border tw-rounded-full tw-overflow-hidden tw-text-semibold">
            <button className={`tw-inline-flex tw-flex-1 tw-items-center tw-justify-center tw-px-6 ${status === "pending" ? "tw-bg-blue-100 tw-text-customBlue" : `${hoverButton}`}`}
            onClick={() => setStatus("pending")}
            >Pending</button>
            <button className={`tw-inline-flex tw-flex-1 tw-items-center tw-justify-center tw-px-6 ${status === "process" ? "tw-bg-blue-100 tw-text-customBlue" : `${hoverButton}`}`}
            onClick={() => setStatus("process")}
            >Process</button>
            <button className={`tw-inline-flex tw-flex-1 tw-items-center tw-justify-center tw-px-6 ${status === "failure" ? "tw-bg-blue-100 tw-text-customBlue" : `${hoverButton}`}`}
            onClick={() => setStatus("failure")}
            >Failure</button>
            <button className={`tw-inline-flex tw-flex-1 tw-items-center tw-justify-center tw-px-6 ${status === "done" ? "tw-bg-blue-100 tw-text-customBlue" : `${hoverButton}`}`}
            onClick={() => setStatus("done")}
            >Done</button>
          </div>

        </div>

        {/* list gd */}
        <div 
          className="tw-mt-12 tw-py-5 tw-overflow-y-auto tw-h-52 tw-relative"
          onScroll={handleScroll}>
          <Suspense>
            <LazyLoading visibleData={visibleData}
                          status={status}
                          OnChange={handleChange}
                          />
          </Suspense>
        </div>

      </div>
    </>
  )
};

export default TransactionManagement;