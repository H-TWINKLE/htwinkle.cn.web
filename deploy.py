import os
import sys

import paramiko
from paramiko import AuthenticationException
from paramiko.client import AutoAddPolicy
from paramiko.ssh_exception import NoValidConnectionsError


def get_file_package(path, key):
    """获取到打包的文件路径，解决时间戳的问题"""
    if os.path.exists(path):
        for name in os.listdir(path):
            if key in name and os.path.isdir(path + '/' + name):
                return name


class SshClient:
    def __init__(self):
        # 创建一个ssh的客户端，用来连接服务器
        self.ssh_client = paramiko.SSHClient()

    def ssh_login(self, host_ip, username, password):
        try:
            # 设置允许连接known_hosts文件中的主机（默认连接不在known_hosts文件中的主机会拒绝连接抛出SSHException）
            self.ssh_client.set_missing_host_key_policy(AutoAddPolicy())
            self.ssh_client.connect(host_ip, port=22, username=username, password=password)
        except AuthenticationException:
            print('username or password error')
            return 1001
        except NoValidConnectionsError:
            print('connect time out')
            return 1002
        except:
            print("Unexpected error:", sys.exc_info()[0])
            return 1003
        return 1000

    def print_info(self, plate, st):
        if st is not None:
            try:
                print('[{0}] : {1}'.format(plate, st.read().decode()))
            except Exception as e:
                print('[{0}] - error : {1}'.format(plate, e))

    def execute_command(self, command):
        """执行命令"""
        stdin, stdout, stderr = self.ssh_client.exec_command(command, timeout=5)
        print('---------- ssl run command : {0} '.format(command))
        self.print_info('print', stdout)
        self.print_info('failure', stderr)

    def ssh_logout(self):
        self.ssh_client.close()

    def execute_cmd(self, cmd):
        """执行cmd命令
        0表示执行成功"""
        return os.system(cmd)


if __name__ == "__main__":
    ssh = SshClient()
    if ssh.ssh_login(host_ip="39.108.48.227", username="root", password="***") == 1000:
        print('---------- ssl login success ! ----------')
        print('---------- start package ----------')
        ssh.execute_cmd('mvn clean package -Dmaven.test.skip=true')
        ssh.execute_command('chmod +755 /root/app_web/HTwinkleWeb/runServer.sh')
        print('---------- stop h_devotion ----------')
        ssh.execute_command('/root/app_web/HTwinkleWeb/runServer.sh stop')
        print('---------- remove h_devotion package ----------')
        ssh.execute_command('rm -rf /root/app_web/HTwinkleWeb/config/')
        ssh.execute_command('rm -rf /root/app_web/HTwinkleWeb/lib/')
        ssh.execute_command('rm -rf /root/app_web/HTwinkleWeb/runServer.sh')
        ssh.execute_command('rm -rf /root/app_web/HTwinkleWeb/runServer.bat')
        ssh.execute_command('rm -rf /root/app_web/HTwinkleWeb/deploy.py')
        print('---------- upload h_devotion  package ----------')
        ## 先获取到文件路径
        path = get_file_package('E:/MyMajor/myidea/HTwinkleWeb/target', 'release')
        ssh.execute_cmd(
            'pscp -r -pw *** ././target/{0}/HTwinkleWeb root@39.108.48.227:/root/app_web/'.format(path))
        ssh.execute_command('chmod +755 /root/app_web/HTwinkleWeb/runServer.sh')
        print('---------- start cuss_tool ----------')
        ssh.execute_command('/root/app_web/HTwinkleWeb/runServer.sh restart')
        print('---------- ssl login out ----------')
        ssh.ssh_logout()

