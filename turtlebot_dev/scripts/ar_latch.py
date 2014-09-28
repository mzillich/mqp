#!/usr/bin/env python

#This node is used to store, retrieve and remove navigation locations
#It subscribes to the robot's pose and receives IDs to save with
#the poses through the service WaypointServerService
import rospy
from std_msgs.msg import Header
from geometry_msgs.msg import Pose, PoseStamped, PoseWithCovarianceStamped
from nav_msgs.msg import OccupancyGrid, MapMetaData
import tf


class ArLatch:

	def __init__(self):
		#Globals
		self.transform = None

		#Subscribers
		self.listener = tf.TransformListener()

		#Publishers
		self.br = tf.TransformBroadcaster()

		#Init Functions
		while self.transform == None:
			self.transform = self.getTransform("odom")

		self.run()

	def getTransform(self, frame):
		try:
			#rospy.loginfo("Getting transform")
			(trans, rot) = self.listener.lookupTransform('ar_marker_0', frame, rospy.Time(0))
			return (trans, rot)
		except:
			#rospy.loginfo("Error getting transform")
			return None

	def sendTransform(self, frame):
		try:
			x = self.transform[0][0]
			y = self.transform[0][1]
			z = self.transform[0][2]
			roll = self.transform[1][0]
			pitch = self.transform[1][1]
			yaw = self.transform[1][2]

			rospy.loginfo("Broadcasting transform!")
			self.br.sendTransform((x, y, z), tf.transformations.quaternion_from_euler(roll, pitch, yaw), \
				rospy.Time.now(), "ar_marker_perm", frame)
		except:
			rospy.loginfo("Error sending transform")

	def run(self):
		while not rospy.is_shutdown():
			self.sendTransform("odom")
			rospy.sleep(.5)

if __name__=="__main__":
	try:
		rospy.init_node('ar_latch')
		ArLatch()
		rospy.spin()
	except rospy.ROSInterruptException:
		rospy.loginfo("AR Latch terminated.")

