
<div class="navbar-header aside bg-danger nav-xs">
  <a class="btn btn-link visible-xs" data-toggle="class:nav-off-screen,open" data-target="#nav,html">
    <i class="icon-list"></i>
  </a>
  <a href="../../app/main/index.htm" class="navbar-brand text-lt">
    <img src="../../images/altsix.png" alt="SmartSuite" style="width:32px">
    <span class="hidden-nav-xs m-l-sm">SmartSuite</span>
  </a>
  <a class="btn btn-link visible-xs" data-toggle="dropdown" data-target=".user">
    <i class="icon-settings"></i>
  </a>
</div>
<!-- 
<div class="navbar-header aside bg-light nav-xs">
  <a class="btn btn-link visible-xs" data-toggle="class:nav-off-screen,open" data-target="#nav,html">
    <i class="icon-list"></i>
  </a>
  <a href="../../app/main/index.htm" class="navbar-brand text-lt">
    <img src="../../images/logo_int.png" alt="SmartSuite" style="width:32px">
    <span class="hidden-nav-xs m-l-sm" style="color:#173553">SmartRetail</span>
  </a>
  <a class="btn btn-link visible-xs" data-toggle="dropdown" data-target=".user">
    <i class="icon-settings"></i>
  </a>
</div>  
-->
<ul class="nav navbar-nav hidden-xs">
  <li>
    <a href="#nav,.navbar-header" data-toggle="class:nav-xs,nav-xs" style="padding-left: 28px;padding-right:28px" class="text-muted">
      <i class="fa fa-indent text"></i>
      <i class="fa fa-dedent text-active"></i>
    </a>
  </li>
</ul>

<form method="post" action="../../app/main/ricerca.htm" class="navbar-form navbar-left input-s-lg m-t m-l-n-xs hidden-xs" role="search">
  <div class="form-group">
    <div class="input-group">
      <span class="input-group-btn">
        <button type="submit" class="btn btn-sm bg-white btn-icon rounded"><i class="fa fa-search"></i></button>
      </span>
      <input type="text"  name="q" class="form-control input-sm no-border rounded" placeholder="<%=propIdx.get("cerca") %>">
    </div>
  </div>
</form>