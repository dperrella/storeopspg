
  <div class="modal-dialog">

    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title">Modal Change Users</h4>
      </div>
      <div class="modal-body" id="Sellll" style="width:100%;">
		<select id="users" name="users" class="selectpicker" title="Change User" data-live-search="true" style="background: #fff; min-width:100%; width:100%; border: 1px;">
			<%=session.getAttribute("Exch") %>
		</select>        
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal"><i class="fa fa-power-off" aria-hidden="true"></i> Close</button>
        <button type="button" class="btn btn-default" onclick="change()" data-dismiss="modal"><i class="icon-user" aria-hidden="true"></i>&nbsp;<i class="fa fa-exchange" aria-hidden="true"></i> Change</button>
      </div>
    </div>

  </div>

