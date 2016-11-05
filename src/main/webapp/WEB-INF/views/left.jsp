<%@page import="com.mjitech.constant.MenuConstants"%>
<%@page import="com.mjitech.constant.WebPageConstants"%>
<%@ page pageEncoding="UTF-8" import="java.util.*,com.mjitech.model.*"%>
<%
	List<Menu> menus = (List<Menu>) request.getAttribute("menus");
%>
<div class="sidebar">
	<div class="blank"></div>
	<div class="sidebar-nav">
		<ul>
			<%
				for (Menu menu : menus) {
			%>
			<li <%if (menu.isSelected()) {%> class="now" <%}%>>
				<h2 <%if (!menu.isSelected() && menu.getSubMenus() != null
						&& menu.getSubMenus().size() > 0) {%>onclick="location.href='<%=MenuConstants.buildMenuUrl(
							request.getContextPath(), menu.getSubMenus().get(0))%>'"<%}%>>
					<i class="icon-side <%=menu.getIconClass()%>"></i>
							<%= menu.getName()%>
						
				</h2> <%
 	if (menu.isSelected()) {
 %>
				<ul class="leve1">
					<!-- 点击添加class now -->
					<%
						for (Menu subMenu : menu.getSubMenus()) {
					%>
					<li <%if (subMenu.isSelected()) {%> class="now" <%}%>><a
						href="<%=MenuConstants.buildMenuUrl(
								request.getContextPath(), subMenu)%>"
						title="<%=subMenu.getName()%>"><i class="arrow"></i><%=subMenu.getName()%></a>
					</li>
					<%
						}
					%>
				</ul> <%
 	}
 %>
			</li>
			<%
				}
			%>
		</ul>
	</div>
</div>