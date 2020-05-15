import csv
from parsel import Selector

from time import sleep
from selenium import webdriver
from selenium.webdriver.common.keys import Keys


def main():
    writer = csv.writer(open('output.csv', 'w+', encoding='utf-8-sig', newline=''))
    writer.writerow(['Name'])

    driver = webdriver.Chrome("./chromedriver")
    driver.get('https://www.linkedin.com/uas/login')

    username = driver.find_element_by_name("session_key")
    username.send_keys('account name (email)')
    sleep(0.2)

    password = driver.find_element_by_name('session_password')
    password.send_keys('PASSWORD')
    sleep(0.2)

    sign_in_button = driver.find_element_by_class_name('btn__primary--large.from__button--floating')
    sign_in_button.click()
    sleep(0.2)

    for n in range(1, 100):
        url = "https://www.linkedin.com/search/results/people/?keywords=software%20engineer&page=" + str(n)
        driver.get(url)

        no_of_pagedowns = 3
        elem = driver.find_element_by_tag_name("body")

        while no_of_pagedowns:
            elem.send_keys(Keys.PAGE_DOWN)
            # sleep(0.2)
            no_of_pagedowns -= 1
        result = driver.find_elements_by_xpath('//span[@class = "name actor-name"]')
        for name in result:
            print(name.text)
            writer.writerow([name.text])

    driver.quit()


if __name__ == '__main__':
    main()
