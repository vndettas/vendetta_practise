import math
import matplotlib.pyplot as plt
m = 1
g = 9.81
dt = 0.1
r = 1.0
time = 20
current_time = 0.0
angle = 1
velocity = 0
angle_euler, velocity_euler = 1.0, 0.0
angle_rk4, velocity_rk4 = 1.0, 0.0

time_list = []
ek_euler_list, ep_euler_list, etot_euler_list = [], [], []
ek_rk4_list, ep_rk4_list, etot_rk4_list = [], [], []

def move_euler(angle, velocity):

    pred_acceleration = calculate_acceleration(angle)
    pred_angle = angle + velocity * dt
    pred_velocity = velocity + pred_acceleration * dt

    new_acceleration = calculate_acceleration(pred_angle)

    final_angle = angle + (pred_velocity + velocity)/2 * dt
    final_velocity = velocity + (pred_acceleration + new_acceleration)/2 * dt

    return final_angle, final_velocity


def move_rk4(angle, velocity):
    da1 = velocity
    dw1 = calculate_acceleration(angle)

    mid_angle1 = angle + da1 * (dt / 2)
    mid_velocity1 = velocity + dw1 * (dt / 2)
    da2 = mid_velocity1
    dw2 = calculate_acceleration(mid_angle1)

    mid_angle2 = angle + da2 * (dt / 2)
    mid_velocity2 = velocity + dw2 * (dt / 2)
    da3 = mid_velocity2
    dw3 = calculate_acceleration(mid_angle2)

    end_angle = angle + da3 * dt
    end_velocity = velocity + dw3 * dt
    da4 = end_velocity
    dw4 = calculate_acceleration(end_angle)

    final_angle = angle + (dt / 6) * (da1 + 2 * da2 + 2 * da3 + da4)
    final_velocity = velocity + (dt / 6) * (dw1 + 2 * dw2 + 2 * dw3 + dw4)

    return final_angle, final_velocity


def calculate_acceleration(angle):
    return - (g / r * math.sin(angle))


while current_time <= time:
    time_list.append(current_time)

    ek_euler = 0.5 * m * (r * velocity_euler) ** 2
    ep_euler = m * g * r * (1 - math.cos(angle_euler))
    ek_euler_list.append(ek_euler)
    ep_euler_list.append(ep_euler)
    etot_euler_list.append(ek_euler + ep_euler)

    ek_rk4 = 0.5 * m * (r * velocity_rk4) ** 2
    ep_rk4 = m * g * r * (1 - math.cos(angle_rk4))
    ek_rk4_list.append(ek_rk4)
    ep_rk4_list.append(ep_rk4)
    etot_rk4_list.append(ek_rk4 + ep_rk4)

    angle_euler, velocity_euler = move_euler(angle_euler, velocity_euler)
    angle_rk4, velocity_rk4 = move_rk4(angle_rk4, velocity_rk4)

    current_time += dt

fig, (ax1, ax2) = plt.subplots(nrows=1, ncols=2, figsize=(14, 6))

ax1.plot(time_list, ek_euler_list, label='Kinetic', color='orange')
ax1.plot(time_list, ep_euler_list, label='Potential', color='blue')
ax1.plot(time_list, etot_euler_list, label='Total energy', color='red', linewidth=2.5)
ax1.set_title("Improved Euler method")
ax1.legend()
ax1.grid(True)

ax2.plot(time_list, ek_rk4_list, label='Kinetic', color='orange')
ax2.plot(time_list, ep_rk4_list, label='Potential', color='blue')
ax2.plot(time_list, etot_rk4_list, label='Total energy', color='red', linewidth=2.5)
ax2.set_title("Runge-Kutta 4 method")
ax2.legend()
ax2.grid(True)

plt.tight_layout()
plt.savefig("lab_result.png")
