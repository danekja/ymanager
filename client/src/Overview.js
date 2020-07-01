import React from 'react';
import './App.css';


const OffsAvailable = (props) => {

   return (
      <div>
         <div className="side-content">
            <div className="side-board column">
               <div className="side-board-heading row">
                  <h3>Overview</h3>
               </div>
            <div className="underline-1"></div>
            <div className="side-board-items column">
               <table border = "0">
                  <tbody>
                     <tr>
                        <th className="th-left">Name</th>
                        <th>Sick Days</th>
                        <th>Holiday</th>
                     </tr>
                     <tr>
                        <td>{props.userName.name}</td>
                        <td className="td-center">{props.userName.sickday}</td>
                        <td className="td-center">{props.userName.holiday}</td>
                     </tr>
                  </tbody>
               </table>
         </div>
      </div>
   </div>
   </div>
   );
}


export default OffsAvailable;