#!/usr/bin/env python

#This node is used to store, retrieve and remove navigation locations
#It subscribes to the robot's pose and receives IDs to save with
#the poses through the service WaypointServerService
import rospy
from std_msgs.msg import Header
from geometry_msgs.msg import Pose, PoseWithCovarianceStamped
from nav_msgs.msg import OccupancyGrid, MapMetaData
import tf


class ZigZag:

	#Creates the service, begins listening to the robot pose, and then spins
	def __init__(self):
		#Globals

		#Subscribers
		#self.pose_sub = rospy.Subscriber('/amcl_pose', PoseWithCovarianceStamped, self.pose_callback)

		self.listener = tf.TransformListener()

		#Publishers
		self.map_pub = rospy.Publisher('/zig_zag_demo/empty_map', OccupancyGrid)
		self.create_empty_map()

		rospy.on_shutdown(self.shutdown)
		self.run()

	def getTransform(self):
		#try:
		(trans, rot) = self.listener.lookupTransform('ar_marker_0', 'camera_rgb_optical_frame', rospy.Time(0))
			#rospy.loginfo(trans)
			#rospy.loginfo(rot)
		#except:
		#	rospy.loginfo("Ouch!")

	def run(self):
		while not rospy.is_shutdown():
			self.getTransform()

	def create_empty_map(self):
		rospy.loginfo("Creating empty map")

		header = Header()
		header.frame_id = 'map'

		meta_data = MapMetaData()
		meta_data.resolution = .2
		meta_data.width = 20
		meta_data.height = 20
		meta_data.origin = Pose()

		rospy.loginfo("Meta Data created")

		data = [0 for i in range(200)]

		rospy.loginfo("Data created")

		empty_map = OccupancyGrid()
		empty_map.header = header
		empty_map.info = meta_data
		empty_map.data = data

		rospy.loginfo("Created empty map:\n")
		print empty_map

		self.map_pub.publish(empty_map)
		rospy.loginfo("Map Published!")

	def shutdown(self):
		pass

if __name__=="__main__":
	try:
		rospy.init_node('zig_zag_demo')
		ZigZag()
		rospy.spin()
	except rospy.ROSInterruptException:
		rospy.loginfo("Zig Zag Demo terminated.")

