<footer class="footer hidden-xs no-padder text-center-nav-xs">
   <div class="bg hidden-xs ">
       <div class="dropdown dropup wrapper-sm clearfix">
         <a href="#" class="dropdown-toggle" data-toggle="dropdown">
           <span class="thumb-sm avatar pull-left m-l-xs">                        
             <img src="../../images/${foto}" class="dker" alt="...">
             <i class="on b-black"></i>
           </span>
           <span class="hidden-nav-xs clear">
             <span class="block m-l">
               <strong class="font-bold text-lt">${username}</strong> 
               <b class="caret"></b>
             </span>
             <span class="text-muted text-xs block m-l">${desc}</span>
           </span>
         </a>
         <ul class="dropdown-menu animated fadeInRight aside text-left">                      
           <li>
             <a href="../../app/main/profilo.htm"><%=propIdx.get("profilo") %></a>
           </li>
           <li>
             <a href="../../app/config/notification-list.htm">
               <%=propIdx.get("notifiche") %>
             </a>
           </li>
           <li>
             <a href="../../app/main/docs.html"><%=propIdx.get("guida") %></a>
           </li>
           <li class="divider"></li>
           <li>
             <a href="../../app/main/modal.lockme.jsp" data-toggle="ajaxModal" ><%=propIdx.get("esci") %></a>
                   </li>
                 </ul>
               </div>
             </div>            
</footer>