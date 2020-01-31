<%@ page 
language="java"
import="pkg.properties.ReadProperties"
import="java.sql.*"
import="pkg.utility.Utility"
contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"
%>
<%
//Legge il file di properties associato alla lingua sul profilo
ReadProperties propLang = new ReadProperties(session.getAttribute("language").toString()+".properties");
%>
<div class="modal-dialog modal-lg">
  <div class="modal-content">
	<div class="modal-header">
		<a class="close" data-dismiss="modal">&times;</a>
		<h3 class="font-thin">Icons</h3>
	</div>
	<div class="modal-body">
		
		<section class="row list-icon" id="classes">
                <div class="col-md-3 col-sm-4">
                    <a href="" onclick="$('#icon').val('icon-user')"  data-dismiss="modal">
                    <i class="icon-user"></i>
                    icon-user
                    </a>
                </div>
                <div class="col-md-3 col-sm-4">
                	<a href="" onclick="$('#icon').val('icon-user-female')"  data-dismiss="modal">
                    <i class="icon-user-female"></i>
                    icon-user-female
                    </a>
                </div>
                <div class="col-md-3 col-sm-4">
                	<a href="" onclick="$('#icon').val('icon-users')"  data-dismiss="modal">
                    <i class="icon-users"></i>
                    icon-users
                    </a>
                </div>
                <div class="col-md-3 col-sm-4">
                	<a href="" onclick="$('#icon').val('icon-user-follow')"  data-dismiss="modal">
                    <i class="icon-user-follow"></i>
                    icon-user-follow
                    </a>
                </div>
                <div class="col-md-3 col-sm-4">
                	<a href="" onclick="$('#icon').val('icon-user-following')"  data-dismiss="modal">
                    <i class="icon-user-following"></i>
                    icon-user-following
                    </a>
                </div>
                <div class="col-md-3 col-sm-4">
                	<a href="" onclick="$('#icon').val('icon-user-unfollow')"  data-dismiss="modal">
                    <i class="icon-user-unfollow"></i>
                    icon-user-unfollow
                    </a>
                </div>
                <div class="col-md-3 col-sm-4">
                	<a href="" onclick="$('#icon').val('icon-trophy')"  data-dismiss="modal">
                    <i class="icon-trophy"></i>
                    icon-trophy
                    </a>
                </div>
                <div class="col-md-3 col-sm-4">
              		<a href="" onclick="$('#icon').val('icon-speedometer')"  data-dismiss="modal">
                    <i class="icon-speedometer"></i>
                    icon-speedometer
                    </a>
                </div>
                <div class="col-md-3 col-sm-4">
                	<a href="" onclick="$('#icon').val('icon-social-youtube')"  data-dismiss="modal">
                    <i class="icon-social-youtube"></i>
                    icon-social-youtube
                    </a>
                </div>
                <div class="col-md-3 col-sm-4">
                    <a href="" onclick="$('#icon').val('icon-social-twitter')"  data-dismiss="modal">
                    <i class="icon-social-twitter"></i>
                    icon-social-twitter
                    </a>
                </div>
                <div class="col-md-3 col-sm-4">
                 	<a href="" onclick="$('#icon').val('icon-social-tumblr')"  data-dismiss="modal">
                    <i class="icon-social-tumblr"></i>
                    icon-social-tumblr
                    </a>
                </div>
                <div class="col-md-3 col-sm-4">
                	<a href="" onclick="$('#icon').val('icon-social-facebook')"  data-dismiss="modal">
                    <i class="icon-social-facebook"></i>
                    icon-social-facebook
                    </a>
                </div>
                <div class="col-md-3 col-sm-4">
               		<a href="" onclick="$('#icon').val('icon-social-dropbox')"  data-dismiss="modal">
                    <i class="icon-social-dropbox"></i>
                    icon-social-dropbox
                    </a>
                </div>
                <div class="col-md-3 col-sm-4">
                	<a href="" onclick="$('#icon').val('icon-social-dribbble')"  data-dismiss="modal">
                    <i class="icon-social-dribbble"></i>
                    icon-social-dribbble
                    </a>
                </div>
                <div class="col-md-3 col-sm-4">
                    <i class="icon-shield"></i>
                    icon-shield
                </div>
                <div class="col-md-3 col-sm-4">
                    <a href="" onclick="$('#icon').val('icon-screen-tablet')"  data-dismiss="modal">
                    <i class="icon-screen-tablet"></i>
                    icon-screen-tablet
                    </a>
                </div>
                <div class="col-md-3 col-sm-4">
                	<a href="" onclick="$('#icon').val('icon-screen-smartphone')"  data-dismiss="modal">
                    <i class="icon-screen-smartphone"></i>
                    icon-screen-smartphone
                    </a>
                </div>
                <div class="col-md-3 col-sm-4">
                    <a href="" onclick="$('#icon').val('icon-screen-desktop')"  data-dismiss="modal">
                    <i class="icon-screen-desktop"></i>
                    icon-screen-desktop
                    </a>
                </div>
                <div class="col-md-3 col-sm-4">
                    <a href="" onclick="$('#icon').val('icon-plane')"  data-dismiss="modal">
                    <i class="icon-plane"></i>
                    icon-plane
                    </a>
                </div>
                <div class="col-md-3 col-sm-4">
                    <a href="" onclick="$('#icon').val('icon-notebook')"  data-dismiss="modal">
                    <i class="icon-notebook"></i>
                    icon-notebook
                    </a>
                </div>
                <div class="col-md-3 col-sm-4">
                    <a href="" onclick="$('#icon').val('icon-moustache')"  data-dismiss="modal">
                    <i class="icon-moustache"></i>
                    icon-moustache
                    </a>
                </div>
                <div class="col-md-3 col-sm-4">
                	<a href="" onclick="$('#icon').val('icon-mouse')"  data-dismiss="modal">
                    <i class="icon-mouse"></i>
                    icon-mouse
                    </a>
                </div>
                <div class="col-md-3 col-sm-4">
                    <a href="" onclick="$('#icon').val('icon-magnet')"  data-dismiss="modal">
                    <i class="icon-magnet"></i>
                    icon-magnet
                    </a>
                </div>
                <div class="col-md-3 col-sm-4">
                    <a href="" onclick="$('#icon').val('icon-magic-wand')"  data-dismiss="modal">
                    <i class="icon-magic-wand"></i>
                    icon-magic-wand
                    </a>
                </div>
                <div class="col-md-3 col-sm-4">
                    <a href="" onclick="$('#icon').val('icon-hourglass')"  data-dismiss="modal">
                    <i class="icon-hourglass"></i>
                    icon-hourglass
                    </a>
                </div>
                <div class="col-md-3 col-sm-4">
                    <a href="" onclick="$('#icon').val('icon-graduation')"  data-dismiss="modal">
                    <i class="icon-graduation"></i>
                    icon-graduation
                    </a>
                </div>
                <div class="col-md-3 col-sm-4">
                    <a href="" onclick="$('#icon').val('icon-ghost')"  data-dismiss="modal">
                    <i class="icon-ghost"></i>
                    icon-ghost
                    </a>
                </div>
                <div class="col-md-3 col-sm-4">
                    <a href="" onclick="$('#icon').val('icon-game-controller')"  data-dismiss="modal">
                    <i class="icon-game-controller"></i>
                    icon-game-controller
                    </a>
                </div>
                <div class="col-md-3 col-sm-4">
                    <a href="" onclick="$('#icon').val('icon-fire')"  data-dismiss="modal">
                    <i class="icon-fire"></i>
                    icon-fire
                    </a>
                </div>
                <div class="col-md-3 col-sm-4">
                    <a href="" onclick="$('#icon').val('icon-eyeglasses')"  data-dismiss="modal">
                    <i class="icon-eyeglasses"></i>
                    icon-eyeglasses
                    </a>
                </div>
                <div class="col-md-3 col-sm-4">
                    <a href="" onclick="$('#icon').val('icon-envelope-open')"  data-dismiss="modal">
                    <i class="icon-envelope-open"></i>
                    icon-envelope-open
                    </a>
                </div>
                <div class="col-md-3 col-sm-4">
                    <a href="" onclick="$('#icon').val('icon-envelope-letter')"  data-dismiss="modal">
                    <i class="icon-envelope-letter"></i>
                    icon-envelope-letter
                    </a>
                </div>
                <div class="col-md-3 col-sm-4">
                    <i class="icon-energy"></i>
                    icon-energy
                </div>
                <div class="col-md-3 col-sm-4">
                    <i class="icon-emoticon-smile"></i>
                    icon-emoticon-smile
                </div>
                <div class="col-md-3 col-sm-4">
                    <i class="icon-disc"></i>
                    icon-disc
                </div>
                <div class="col-md-3 col-sm-4">
                    <i class="icon-cursor-move"></i>
                    icon-cursor-move
                </div>
                <div class="col-md-3 col-sm-4">
                    <i class="icon-crop"></i>
                    icon-crop
                </div>
                <div class="col-md-3 col-sm-4">
                    <i class="icon-credit-card"></i>
                    icon-credit-card
                </div>
                <div class="col-md-3 col-sm-4">
                    <i class="icon-chemistry"></i>
                    icon-chemistry
                </div>
                <div class="col-md-3 col-sm-4">
                    <i class="icon-bell"></i>
                    icon-bell
                </div>
                <div class="col-md-3 col-sm-4">
                    <i class="icon-badge"></i>
                    icon-badge
                </div>
                <div class="col-md-3 col-sm-4">
                    <i class="icon-anchor"></i>
                    icon-anchor
                </div>
                <div class="col-md-3 col-sm-4">
                    <i class="icon-action-redo"></i>
                    icon-action-redo
                </div>
                <div class="col-md-3 col-sm-4">
                    <i class="icon-action-undo"></i>
                    icon-action-undo
                </div>
                <div class="col-md-3 col-sm-4">
                    <i class="icon-bag"></i>
                    icon-bag
                </div>
                <div class="col-md-3 col-sm-4">
                    <i class="icon-basket"></i>
                    icon-basket
                </div>
                <div class="col-md-3 col-sm-4">
                    <i class="icon-basket-loaded"></i>
                    icon-basket-loaded
                </div>
                <div class="col-md-3 col-sm-4">
                    <i class="icon-book-open"></i>
                    icon-book-open
                </div>
                <div class="col-md-3 col-sm-4">
                    <i class="icon-briefcase"></i>
                    icon-briefcase
                </div>
                <div class="col-md-3 col-sm-4">
                    <i class="icon-bubbles"></i>
                    icon-bubbles
                </div>
                <div class="col-md-3 col-sm-4">
                    <i class="icon-calculator"></i>
                    icon-calculator
                </div>
                <div class="col-md-3 col-sm-4">
                    <i class="icon-call-end"></i>
                    icon-call-end
                </div>
                <div class="col-md-3 col-sm-4">
                    <i class="icon-call-in"></i>
                    icon-call-in
                </div>
                <div class="col-md-3 col-sm-4">
                    <i class="icon-call-out"></i>
                    icon-call-out
                </div>
                <div class="col-md-3 col-sm-4">
                    <i class="icon-compass"></i>
                    icon-compass
                </div>
                <div class="col-md-3 col-sm-4">
                    <i class="icon-cup"></i>
                    icon-cup
                </div>
                <div class="col-md-3 col-sm-4">
                    <i class="icon-diamond"></i>
                    icon-diamond
                </div>
                <div class="col-md-3 col-sm-4">
                    <i class="icon-direction"></i>
                    icon-direction
                </div>
                <div class="col-md-3 col-sm-4">
                    <i class="icon-directions"></i>
                    icon-directions
                </div>
                <div class="col-md-3 col-sm-4">
                    <i class="icon-docs"></i>
                    icon-docs
                </div>
                <div class="col-md-3 col-sm-4">
                    <i class="icon-drawer"></i>
                    icon-drawer
                </div>
                <div class="col-md-3 col-sm-4">
                    <i class="icon-drop"></i>
                    icon-drop
                </div>
                <div class="col-md-3 col-sm-4">
                    <i class="icon-earphones"></i>
                    icon-earphones
                </div>
                <div class="col-md-3 col-sm-4">
                    <i class="icon-earphones-alt"></i>
                    icon-earphones-alt
                </div>
                <div class="col-md-3 col-sm-4">
                    <i class="icon-feed"></i>
                    icon-feed
                </div>
                <div class="col-md-3 col-sm-4">
                    <i class="icon-film"></i>
                    icon-film
                </div>
                <div class="col-md-3 col-sm-4">
                    <i class="icon-folder-alt"></i>
                    icon-folder-alt
                </div>
                <div class="col-md-3 col-sm-4">
                    <i class="icon-frame"></i>
                    icon-frame
                </div>
                <div class="col-md-3 col-sm-4">
                    <i class="icon-globe"></i>
                    icon-globe
                </div>
                <div class="col-md-3 col-sm-4">
                    <i class="icon-globe-alt"></i>
                    icon-globe-alt
                </div>
                <div class="col-md-3 col-sm-4">
                    <i class="icon-handbag"></i>
                    icon-handbag
                </div>
                <div class="col-md-3 col-sm-4">
                    <i class="icon-layers"></i>
                    icon-layers
                </div>
                <div class="col-md-3 col-sm-4">
                    <i class="icon-map"></i>
                    icon-map
                </div>
                <div class="col-md-3 col-sm-4">
                    <i class="icon-picture"></i>
                    icon-picture
                </div>
                <div class="col-md-3 col-sm-4">
                    <i class="icon-pin"></i>
                    icon-pin
                </div>
                <div class="col-md-3 col-sm-4">
                    <i class="icon-playlist"></i>
                    icon-playlist
                </div>
                <div class="col-md-3 col-sm-4">
                    <i class="icon-present"></i>
                    icon-present
                </div>
                <div class="col-md-3 col-sm-4">
                    <i class="icon-printer"></i>
                    icon-printer
                </div>
                <div class="col-md-3 col-sm-4">
                    <i class="icon-puzzle"></i>
                    icon-puzzle
                </div>
                <div class="col-md-3 col-sm-4">
                    <i class="icon-speech"></i>
                    icon-speech
                </div>
                <div class="col-md-3 col-sm-4">
                    <i class="icon-vector"></i>
                    icon-vector
                </div>
                <div class="col-md-3 col-sm-4">
                    <i class="icon-wallet"></i>
                    icon-wallet
                </div>
                <div class="col-md-3 col-sm-4">
                    <i class="icon-arrow-down"></i>
                    icon-arrow-down
                </div>
                <div class="col-md-3 col-sm-4">
                    <i class="icon-arrow-left"></i>
                    icon-arrow-left
                </div>
                <div class="col-md-3 col-sm-4">
                    <i class="icon-arrow-right"></i>
                    icon-arrow-right
                </div>
                <div class="col-md-3 col-sm-4">
                    <i class="icon-arrow-up"></i>
                    icon-arrow-up
                </div>
                <div class="col-md-3 col-sm-4">
                    <i class="icon-bar-chart"></i>
                    icon-bar-chart
                </div>
                <div class="col-md-3 col-sm-4">
                    <i class="icon-bulb"></i>
                    icon-bulb
                </div>
                <div class="col-md-3 col-sm-4">
                    <i class="icon-calendar"></i>
                    icon-calendar
                </div>
                <div class="col-md-3 col-sm-4">
                    <i class="icon-control-end"></i>
                    icon-control-end
                </div>
                <div class="col-md-3 col-sm-4">
                    <i class="icon-control-forward"></i>
                    icon-control-forward
                </div>
                <div class="col-md-3 col-sm-4">
                    <i class="icon-control-pause"></i>
                    icon-control-pause
                </div>
                <div class="col-md-3 col-sm-4">
                    <i class="icon-control-play"></i>
                    icon-control-play
                </div>
                <div class="col-md-3 col-sm-4">
                    <i class="icon-control-rewind"></i>
                    icon-control-rewind
                </div>
                <div class="col-md-3 col-sm-4">
                    <i class="icon-control-start"></i>
                    icon-control-start
                </div>
                <div class="col-md-3 col-sm-4">
                    <i class="icon-cursor"></i>
                    icon-cursor
                </div>
                <div class="col-md-3 col-sm-4">
                    <i class="icon-dislike"></i>
                    icon-dislike
                </div>
                <div class="col-md-3 col-sm-4">
                    <i class="icon-equalizer"></i>
                    icon-equalizer
                </div>
                <div class="col-md-3 col-sm-4">
                    <i class="icon-graph"></i>
                    icon-graph
                </div>
                <div class="col-md-3 col-sm-4">
                    <i class="icon-grid"></i>
                    icon-grid
                </div>
                <div class="col-md-3 col-sm-4">
                    <i class="icon-home"></i>
                    icon-home
                </div>
                <div class="col-md-3 col-sm-4">
                    <i class="icon-like"></i>
                    icon-like
                </div>
                <div class="col-md-3 col-sm-4">
                    <i class="icon-list"></i>
                    icon-list
                </div>
                <div class="col-md-3 col-sm-4">
                    <i class="icon-login"></i>
                    icon-login
                </div>
                <div class="col-md-3 col-sm-4">
                    <i class="icon-logout"></i>
                    icon-logout
                </div>
                <div class="col-md-3 col-sm-4">
                    <i class="icon-loop"></i>
                    icon-loop
                </div>
                <div class="col-md-3 col-sm-4">
                    <i class="icon-microphone"></i>
                    icon-microphone
                </div>
                <div class="col-md-3 col-sm-4">
                    <i class="icon-music-tone"></i>
                    icon-music-tone
                </div>
                <div class="col-md-3 col-sm-4">
                    <i class="icon-music-tone-alt"></i>
                    icon-music-tone-alt
                </div>
                <div class="col-md-3 col-sm-4">
                    <i class="icon-note"></i>
                    icon-note
                </div>
                <div class="col-md-3 col-sm-4">
                    <i class="icon-pencil"></i>
                    icon-pencil
                </div>
                <div class="col-md-3 col-sm-4">
                    <i class="icon-pie-chart"></i>
                    icon-pie-chart
                </div>
                <div class="col-md-3 col-sm-4">
                    <i class="icon-question"></i>
                    icon-question
                </div>
                <div class="col-md-3 col-sm-4">
                    <i class="icon-rocket"></i>
                    icon-rocket
                </div>
                <div class="col-md-3 col-sm-4">
                    <i class="icon-share"></i>
                    icon-share
                </div>
                <div class="col-md-3 col-sm-4">
                    <i class="icon-share-alt"></i>
                    icon-share-alt
                </div>
                <div class="col-md-3 col-sm-4">
                    <i class="icon-shuffle"></i>
                    icon-shuffle
                </div>
                <div class="col-md-3 col-sm-4">
                    <i class="icon-size-actual"></i>
                    icon-size-actual
                </div>
                <div class="col-md-3 col-sm-4">
                    <i class="icon-size-fullscreen"></i>
                    icon-size-fullscreen
                </div>
                <div class="col-md-3 col-sm-4">
                    <i class="icon-support"></i>
                    icon-support
                </div>
                <div class="col-md-3 col-sm-4">
                    <i class="icon-tag"></i>
                    icon-tag
                </div>
                <div class="col-md-3 col-sm-4">
                    <i class="icon-trash"></i>
                    icon-trash
                </div>
                <div class="col-md-3 col-sm-4">
                    <i class="icon-umbrella"></i>
                    icon-umbrella
                </div>
                <div class="col-md-3 col-sm-4">
                    <i class="icon-wrench"></i>
                    icon-wrench
                </div>
                <div class="col-md-3 col-sm-4">
                    <i class="icon-ban"></i>
                    icon-ban
                </div>
                <div class="col-md-3 col-sm-4">
                    <i class="icon-bubble"></i>
                    icon-bubble
                </div>
                <div class="col-md-3 col-sm-4">
                    <i class="icon-camcorder"></i>
                    icon-camcorder
                </div>
                <div class="col-md-3 col-sm-4">
                    <i class="icon-camera"></i>
                    icon-camera
                </div>
                <div class="col-md-3 col-sm-4">
                    <i class="icon-check"></i>
                    icon-check
                </div>
                <div class="col-md-3 col-sm-4">
                    <i class="icon-clock"></i>
                    icon-clock
                </div>
                <div class="col-md-3 col-sm-4">
                    <i class="icon-close"></i>
                    icon-close
                </div>
                <div class="col-md-3 col-sm-4">
                    <i class="icon-cloud-download"></i>
                    icon-cloud-download
                </div>
                <div class="col-md-3 col-sm-4">
                    <i class="icon-cloud-upload"></i>
                    icon-cloud-upload
                </div>
                <div class="col-md-3 col-sm-4">
                    <i class="icon-doc"></i>
                    icon-doc
                </div>
                <div class="col-md-3 col-sm-4">
                    <i class="icon-envelope"></i>
                    icon-envelope
                </div>
                <div class="col-md-3 col-sm-4">
                    <i class="icon-eye"></i>
                    icon-eye
                </div>
                <div class="col-md-3 col-sm-4">
                    <i class="icon-flag"></i>
                    icon-flag
                </div>
                <div class="col-md-3 col-sm-4">
                    <i class="icon-folder"></i>
                    icon-folder
                </div>
                <div class="col-md-3 col-sm-4">
                    <i class="icon-heart"></i>
                    icon-heart
                </div>
                <div class="col-md-3 col-sm-4">
                    <i class="icon-info"></i>
                    icon-info
                </div>
                <div class="col-md-3 col-sm-4">
                    <i class="icon-key"></i>
                    icon-key
                </div>
                <div class="col-md-3 col-sm-4">
                    <i class="icon-link"></i>
                    icon-link
                </div>
                <div class="col-md-3 col-sm-4">
                    <i class="icon-lock"></i>
                    icon-lock
                </div>
                <div class="col-md-3 col-sm-4">
                    <i class="icon-lock-open"></i>
                    icon-lock-open
                </div>
                <div class="col-md-3 col-sm-4">
                    <i class="icon-magnifier"></i>
                    icon-magnifier
                </div>
                <div class="col-md-3 col-sm-4">
                    <i class="icon-magnifier-add"></i>
                    icon-magnifier-add
                </div>
                <div class="col-md-3 col-sm-4">
                    <i class="icon-magnifier-remove"></i>
                    icon-magnifier-remove
                </div>
                <div class="col-md-3 col-sm-4">
                    <i class="icon-paper-clip"></i>
                    icon-paper-clip
                </div>
                <div class="col-md-3 col-sm-4">
                    <i class="icon-paper-plane"></i>
                    icon-paper-plane
                </div>
                <div class="col-md-3 col-sm-4">
                    <i class="icon-plus"></i>
                    icon-plus
                </div>
                <div class="col-md-3 col-sm-4">
                    <i class="icon-pointer"></i>
                    icon-pointer
                </div>
                <div class="col-md-3 col-sm-4">
                    <i class="icon-power"></i>
                    icon-power
                </div>
                <div class="col-md-3 col-sm-4">
                    <i class="icon-refresh"></i>
                    icon-refresh
                </div>
                <div class="col-md-3 col-sm-4">
                    <i class="icon-reload"></i>
                    icon-reload
                </div>
                <div class="col-md-3 col-sm-4">
                    <i class="icon-settings"></i>
                    icon-settings
                </div>
                <div class="col-md-3 col-sm-4">
                    <i class="icon-star"></i>
                    icon-star
                </div>
                <div class="col-md-3 col-sm-4">
                    <i class="icon-symbol-female"></i>
                    icon-symbol-female
                </div>
                <div class="col-md-3 col-sm-4">
                    <i class="icon-symbol-male"></i>
                    icon-symbol-male
                </div>
                <div class="col-md-3 col-sm-4">
                    <i class="icon-target"></i>
                    icon-target
                </div>
                <div class="col-md-3 col-sm-4">
                    <i class="icon-volume-1"></i>
                    icon-volume-1
                </div>
                <div class="col-md-3 col-sm-4">
                    <i class="icon-volume-2"></i>
                    icon-volume-2
                </div>
                <div class="col-md-3 col-sm-4">
                    <i class="icon-volume-off"></i>
                    icon-volume-off
                </div>
              </section>
       
	    
	</div>
	<div class="modal-footer">
		<a class="btn btn-default" data-dismiss="modal"><%=propLang.get("pop_ap_chiudi") %></a>
	</div>
  </div>
</div>
