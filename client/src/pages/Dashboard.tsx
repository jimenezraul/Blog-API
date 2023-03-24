import { useEffect, useState } from 'react';
import { FetchData } from '../utils/FetchData';

const Dashboard = () => {
  const [data, setData] = useState([]);

  useEffect(() => {
    async function getData() {
      const response = await FetchData('/api/v1/users', 'GET');
      
      setData(response);
    }
    getData();
  }, []);

  return (
    <div>
      <h1>Dashboard</h1>
      {data &&
        data?.map((user: any) => (
          <div key={user.id}>
            <p>{user.name}</p>
            <p>{user.username}</p>
          </div>
        ))}
    </div>
  );
};

export default Dashboard;
